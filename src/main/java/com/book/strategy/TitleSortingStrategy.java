package com.book.strategy;

import com.book.dto.BookDTO;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleSortingStrategy implements SortingStrategy {
    @Override
    public List<BookDTO> sort(List<BookDTO> books) {
        return books.stream()
                .sorted(Comparator.comparing(BookDTO::getTitle))
                .collect(Collectors.toList());
    }
}