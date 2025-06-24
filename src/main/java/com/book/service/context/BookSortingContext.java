package com.book.service.context;

import com.book.dto.BookDTO;
import com.book.strategy.SortingStrategy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class BookSortingContext {
    private final Map<String, SortingStrategy> strategies;

    public BookSortingContext(List<SortingStrategy> strategyList) {
        strategies = strategyList.stream()
                .collect(Collectors.toMap(
                        strategy -> strategy.getClass().getSimpleName(),
                        Function.identity()
                ));
    }

    public List<BookDTO> executeSort(String strategyName, List<BookDTO> books) {
        SortingStrategy strategy = strategies.get(strategyName + "SortingStrategy");
        if (strategy == null) {
            throw new IllegalArgumentException("Unknown sorting strategy: " + strategyName);
        }
        return strategy.sort(books);
    }
}