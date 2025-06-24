package com.book;

import com.book.convert.BookMapper;
import com.book.dto.BookDTO;
import com.book.dto.BookQueryDTO;
import com.book.dto.PageResult;
import com.book.mapper.BooksMapper;
import com.book.po.Book;
import com.book.service.context.BookSortingContext;
import com.book.service.impl.BookServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {
    @Mock
    private BooksMapper booksMapper;
    @Mock
    private BookMapper bookMapper;
    @Mock
    private BookSortingContext bookSortingContext;
    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBook_success() {
        BookDTO dto = buildBookDTO();
        Book entity = buildBook();
        when(booksMapper.existsByIsbn(dto.getIsbn())).thenReturn(false);
        when(bookMapper.toEntity(dto)).thenReturn(entity);
        doNothing().when(booksMapper).insert(entity);
        when(bookMapper.toDTO(entity)).thenReturn(dto);
        BookDTO result = bookService.createBook(dto);
        assertEquals(dto, result);
    }

    @Test
    void testCreateBook_isbnExists() {
        BookDTO dto = buildBookDTO();
        when(booksMapper.existsByIsbn(dto.getIsbn())).thenReturn(true);
        assertThrows(RuntimeException.class, () -> bookService.createBook(dto));
    }

    @Test
    void testGetBookById_found() {
        Book entity = buildBook();
        BookDTO dto = buildBookDTO();
        when(booksMapper.findById(1L)).thenReturn(entity);
        when(bookMapper.toDTO(entity)).thenReturn(dto);
        BookDTO result = bookService.getBookById(1L);
        assertEquals(dto, result);
    }

    @Test
    void testGetBookById_notFound() {
        when(booksMapper.findById(1L)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void testListBooks() {
        BookQueryDTO queryDTO = new BookQueryDTO();
        queryDTO.setPageNum(1);
        queryDTO.setPageSize(10);
        Book entity = buildBook();
        BookDTO dto = buildBookDTO();
        when(booksMapper.findByCondition(any())).thenReturn(Collections.singletonList(entity));
        when(booksMapper.countByCondition(any())).thenReturn(1L);
        when(bookMapper.toDTO(entity)).thenReturn(dto);
        PageResult<BookDTO> result = bookService.listBooks(queryDTO);
        assertEquals(1, result.getRecords().size());
        assertEquals(1, result.getTotal());
    }

    @Test
    void testGetAllBooks() {
        Book entity = buildBook();
        BookDTO dto = buildBookDTO();
        when(booksMapper.findAll()).thenReturn(Collections.singletonList(entity));
        when(bookMapper.toDTO(entity)).thenReturn(dto);
        List<BookDTO> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateBook_success() {
        BookDTO dto = buildBookDTO();
        Book entity = buildBook();
        when(booksMapper.findById(1L)).thenReturn(entity);
        when(bookMapper.toEntity(dto)).thenReturn(entity);
        doNothing().when(booksMapper).update(entity);
        when(bookMapper.toDTO(entity)).thenReturn(dto);
        BookDTO result = bookService.updateBook(1L, dto);
        assertEquals(dto, result);
    }

    @Test
    void testUpdateBook_isbnExists() {
        BookDTO dto = buildBookDTO();
        Book entity = buildBook();
        entity.setIsbn("old-isbn");
        dto.setIsbn("new-isbn");
        when(booksMapper.findById(1L)).thenReturn(entity);
        when(booksMapper.existsByIsbn("new-isbn")).thenReturn(true);
        assertThrows(RuntimeException.class, () -> bookService.updateBook(1L, dto));
    }

    @Test
    void testDeleteBook_success() {
        Book entity = buildBook();
        when(booksMapper.findById(1L)).thenReturn(entity);
        doNothing().when(booksMapper).delete(1L);
        assertDoesNotThrow(() -> bookService.deleteBook(1L));
    }

    @Test
    void testDeleteBook_notFound() {
        when(booksMapper.findById(1L)).thenReturn(null);
        assertThrows(RuntimeException.class, () -> bookService.deleteBook(1L));
    }

    @Test
    void testIsIsbnExists() {
        when(booksMapper.existsByIsbn("isbn")).thenReturn(true);
        assertTrue(bookService.isIsbnExists("isbn"));
    }

    @Test
    void testGetBooksSorted() {
        List<BookDTO> books = Arrays.asList(buildBookDTO(), buildBookDTO());
        when(booksMapper.findAll()).thenReturn(Collections.emptyList());
        when(bookSortingContext.executeSort(anyString(), anyList())).thenReturn(books);
        List<BookDTO> result = bookService.getBooksSorted("Title");
        assertEquals(2, result.size());
    }

    private BookDTO buildBookDTO() {
        return BookDTO.builder()
                .id(1L)
                .title("测试书籍")
                .isbn("1234567890")
                .authorId(1L)
                .authorName("作者")
                .publisherId(1L)
                .publisherName("出版社")
                .publicationDate(LocalDate.now())
                .price(new BigDecimal("99.99"))
                .stockQuantity(10)
                .coverImageUrl("url")
                .description("描述")
                .status(1)
                .build();
    }

    private Book buildBook() {
        Book book = new Book();
        book.setId(1L);
        book.setTitle("测试书籍");
        book.setIsbn("1234567890");
        book.setAuthorId(1L);
        book.setAuthorName("作者");
        book.setPublisherId(1L);
        book.setPublisherName("出版社");
        book.setPublicationDate(LocalDate.now());
        book.setPrice(new BigDecimal("99.99"));
        book.setStockQuantity(10);
        book.setCoverImageUrl("url");
        book.setDescription("描述");
        book.setStatus(1);
        return book;
    }
} 