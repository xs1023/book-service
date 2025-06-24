package com.book.factory;

import com.book.dto.BookDTO;
import com.book.po.Book;

public interface BookFactory {
    Book createBook(BookDTO bookDTO);
}