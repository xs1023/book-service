package com.book.factory;

import com.book.dto.BookDTO;
import com.book.enm.BookType;
import com.book.po.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BookFactoryContextTest {

    @Mock
    private EducationalBookFactory educationalBookFactory;

    @Mock
    private FictionBookFactory fictionBookFactory;

    private BookFactoryContext bookFactoryContext;

    @BeforeEach
    void setUp() {
        List<BookFactory> factoryList = Arrays.asList(educationalBookFactory, fictionBookFactory);
        bookFactoryContext = new BookFactoryContext(factoryList);
    }

    @Test
    void testCreateEducationalBook() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("Java编程思想")
                .isbn("978-7-111-54742-6")
                .authorId(1L)
                .publisherId(1L)
                .publicationDate(LocalDate.of(2020, 1, 1))
                .price(new BigDecimal("89.00"))
                .stockQuantity(100)
                .build();

        Book expectedBook = Book.builder()
                .title("Java编程思想")
                .isbn("978-7-111-54742-6")
                .category("EDUCATIONAL")
                .build();

        when(educationalBookFactory.createBook(any(BookDTO.class))).thenReturn(expectedBook);

        // 执行测试
        Book result = bookFactoryContext.createBook(BookType.EDUCATIONAL, bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("EDUCATIONAL", result.getCategory());
        assertEquals("Java编程思想", result.getTitle());
        assertEquals("978-7-111-54742-6", result.getIsbn());
    }

    @Test
    void testCreateFictionBook() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("三体")
                .isbn("978-7-5366-9293-0")
                .authorId(2L)
                .publisherId(2L)
                .publicationDate(LocalDate.of(2008, 1, 1))
                .price(new BigDecimal("68.00"))
                .stockQuantity(50)
                .build();

        Book expectedBook = Book.builder()
                .title("三体")
                .isbn("978-7-5366-9293-0")
                .category("FICTION")
                .build();

        when(fictionBookFactory.createBook(any(BookDTO.class))).thenReturn(expectedBook);

        // 执行测试
        Book result = bookFactoryContext.createBook(BookType.FICTION, bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("FICTION", result.getCategory());
        assertEquals("三体", result.getTitle());
        assertEquals("978-7-5366-9293-0", result.getIsbn());
    }

    @Test
    void testCreateBookWithUnsupportedType() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("测试书籍")
                .isbn("978-7-111-11111-1")
                .build();

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookFactoryContext.createBook(BookType.CHILDREN, bookDTO)
        );

        assertEquals("Unsupported book type: CHILDREN", exception.getMessage());
    }

    @Test
    void testCreateBookWithNullType() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("测试书籍")
                .isbn("978-7-111-11111-1")
                .build();

        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> bookFactoryContext.createBook(null, bookDTO));
    }

    @Test
    void testCreateBookWithNullBookDTO() {
        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> bookFactoryContext.createBook(BookType.FICTION, null));
    }
} 