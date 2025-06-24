package com.book.factory;

import com.book.dto.BookDTO;
import com.book.po.Book;
import org.springframework.stereotype.Component;

@Component
public class FictionBookFactory implements BookFactory {
    @Override
    public Book createBook(BookDTO bookDTO) {
        Book book = new Book();
        // 小说类特有处理
        book.setCategory("FICTION");
        return book;
    }
}
