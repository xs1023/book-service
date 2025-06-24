package com.book.service.impl;

import com.book.convert.BookMapper;
import com.book.dto.BookDTO;
import com.book.dto.BookQueryDTO;
import com.book.dto.PageResult;
import com.book.mapper.BooksMapper;
import com.book.po.Book;
import com.book.service.BookService;
import com.book.service.context.BookSortingContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BooksMapper booksMapper;
    private final BookMapper bookMapper;
    private final BookSortingContext bookSortingContext;

    @Override
    @Transactional
    public BookDTO createBook(BookDTO bookDTO) {
        log.info("Creating new book with title: {}", bookDTO.getTitle());

        // 验证ISBN唯一性
        if (isIsbnExists(bookDTO.getIsbn())) {
            throw new RuntimeException("ISBN already exists: " + bookDTO.getIsbn());
        }

        Book book = bookMapper.toEntity(bookDTO);
        booksMapper.insert(book);

        return bookMapper.toDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public BookDTO getBookById(Long id) {
        log.debug("Fetching book with id: {}", id);
        Book book = booksMapper.findById(id);
        if (book == null) {
            throw new RuntimeException("Book not found with id: " + id);
        }
        return bookMapper.toDTO(book);
    }

    @Override
    @Transactional(readOnly = true)
    public PageResult<BookDTO> listBooks(BookQueryDTO queryDTO) {
        log.debug("Fetching paginated books with query: {}", queryDTO);

        // 计算分页偏移量
        int offset = (queryDTO.getPageNum() - 1) * queryDTO.getPageSize();
        queryDTO.setPageNum(offset);

        // 执行查询
        List<Book> books = booksMapper.findByCondition(queryDTO);
        long total = booksMapper.countByCondition(queryDTO);

        // 转换为DTO
        List<BookDTO> bookDTOs = books.stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());

        // 返回自定义分页结果
        return new PageResult<>(
                bookDTOs,
                total,
                queryDTO.getPageNum(),
                queryDTO.getPageSize()
        );
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookDTO> getAllBooks() {
        log.debug("Fetching all books");
        return booksMapper.findAll().stream()
                .map(bookMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        log.info("Updating book with id: {}", id);

        Book existingBook = booksMapper.findById(id);

        // 验证ISBN是否被其他书籍使用
        if (!existingBook.getIsbn().equals(bookDTO.getIsbn()) &&
                isIsbnExists(bookDTO.getIsbn())) {
            throw new RuntimeException("ISBN already exists: " + bookDTO.getIsbn());
        }

        Book updatedBook = bookMapper.toEntity(bookDTO);
        updatedBook.setId(id);
        booksMapper.update(updatedBook);

        return bookMapper.toDTO(updatedBook);
    }

    @Override
    @Transactional
    public void deleteBook(Long id) {
        log.info("Deleting book with id: {}", id);
        if (booksMapper.findById(id) == null) {
            throw new RuntimeException("Book not found with id: " + id);
        }

        booksMapper.delete(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean isIsbnExists(String isbn) {
        return booksMapper.existsByIsbn(isbn);
    }

    /**
     * 策略模式
     *
     * @param sortBy
     * @return {@link List }<{@link BookDTO }>
     */
    @Override
    public List<BookDTO> getBooksSorted(String sortBy) {
        List<BookDTO> books = getAllBooks(); // 获取所有书籍
        return bookSortingContext.executeSort(sortBy, books);
    }
}