package com.book.factory;

import com.book.dto.BookDTO;
import com.book.enm.BookType;
import com.book.po.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 工厂模式
 *
 * @author Sheng.Xu
 * @date 2025/06/24
 */
@Service
public class BookFactoryContext {
    private final Map<BookType, BookFactory> factories;

    public BookFactoryContext(List<BookFactory> factoryList) {
        factories = factoryList.stream()
                .collect(Collectors.toMap(
                        factory -> BookType.valueOf(
                                factory.getClass().getSimpleName()
                                        .replace("BookFactory", "")
                                        .toUpperCase()
                        ),
                        Function.identity()
                ));
    }

    public Book createBook(BookType type, BookDTO bookDTO) {
        BookFactory factory = factories.get(type);
        if (factory == null) {
            throw new IllegalArgumentException("Unsupported book type: " + type);
        }
        return factory.createBook(bookDTO);
    }
}