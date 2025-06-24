package com.book.service;

import com.book.dto.BookDTO;
import com.book.dto.BookQueryDTO;
import com.book.dto.PageResult;

import java.util.List;

public interface BookService {
    BookDTO createBook(BookDTO bookDTO);

    BookDTO getBookById(Long id);

    PageResult<BookDTO> listBooks(BookQueryDTO queryDTO);

    List<BookDTO> getAllBooks();

    BookDTO updateBook(Long id, BookDTO bookDTO);

    void deleteBook(Long id);

    boolean isIsbnExists(String isbn);

    List<BookDTO> getBooksSorted(String sortBy);
}
