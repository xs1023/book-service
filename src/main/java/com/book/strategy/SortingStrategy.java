package com.book.strategy;

import com.book.dto.BookDTO;

import java.util.List;

public interface SortingStrategy {
    List<BookDTO> sort(List<BookDTO> books);
}
