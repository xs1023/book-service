package com.book.service.context;

import com.book.dto.BookDTO;
import com.book.strategy.SortingStrategy;
import com.book.strategy.TitleSortingStrategy;
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
class BookSortingContextTest {

    @Mock
    private SortingStrategy mockSortingStrategy;

    private TitleSortingStrategy titleSortingStrategy;
    private BookSortingContext bookSortingContext;

    @BeforeEach
    void setUp() {
        titleSortingStrategy = new TitleSortingStrategy();
        List<SortingStrategy> strategyList = Arrays.asList(titleSortingStrategy, mockSortingStrategy);
        bookSortingContext = new BookSortingContext(strategyList);
    }

    @Test
    void testExecuteSortWithTitleStrategy() {
        // 准备测试数据
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder()
                        .title("三体")
                        .isbn("978-7-5366-9293-0")
                        .authorId(1L)
                        .publisherId(1L)
                        .publicationDate(LocalDate.of(2008, 1, 1))
                        .price(new BigDecimal("68.00"))
                        .build(),
                BookDTO.builder()
                        .title("Java编程思想")
                        .isbn("978-7-111-54742-6")
                        .authorId(2L)
                        .publisherId(2L)
                        .publicationDate(LocalDate.of(2020, 1, 1))
                        .price(new BigDecimal("89.00"))
                        .build(),
                BookDTO.builder()
                        .title("算法导论")
                        .isbn("978-7-111-40713-0")
                        .authorId(3L)
                        .publisherId(3L)
                        .publicationDate(LocalDate.of(2012, 1, 1))
                        .price(new BigDecimal("128.00"))
                        .build()
        );

        // 执行测试
        List<BookDTO> result = bookSortingContext.executeSort("Title", books);

        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Java编程思想", result.get(0).getTitle());
        assertEquals("三体", result.get(1).getTitle());
        assertEquals("算法导论", result.get(2).getTitle());
    }

    @Test
    void testExecuteSortWithMockStrategy() {
        // 准备测试数据
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder().title("书籍1").build(),
                BookDTO.builder().title("书籍2").build()
        );

        List<BookDTO> expectedSortedBooks = Arrays.asList(
                BookDTO.builder().title("书籍2").build(),
                BookDTO.builder().title("书籍1").build()
        );

        when(mockSortingStrategy.sort(any())).thenReturn(expectedSortedBooks);

        // 执行测试
        List<BookDTO> result = bookSortingContext.executeSort("Mock", books);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("书籍2", result.get(0).getTitle());
        assertEquals("书籍1", result.get(1).getTitle());
    }

    @Test
    void testExecuteSortWithUnknownStrategy() {
        // 准备测试数据
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder().title("测试书籍").build()
        );

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> bookSortingContext.executeSort("Unknown", books)
        );

        assertEquals("Unknown sorting strategy: Unknown", exception.getMessage());
    }

    @Test
    void testExecuteSortWithEmptyBookList() {
        // 准备空列表
        List<BookDTO> books = Arrays.asList();

        // 执行测试
        List<BookDTO> result = bookSortingContext.executeSort("Title", books);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testExecuteSortWithNullBookList() {
        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> bookSortingContext.executeSort("Title", null));
    }

    @Test
    void testExecuteSortWithNullStrategyName() {
        // 准备测试数据
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder().title("测试书籍").build()
        );

        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> bookSortingContext.executeSort(null, books));
    }

    @Test
    void testExecuteSortWithSingleBook() {
        // 准备单本书籍
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder()
                        .title("单本书籍")
                        .isbn("978-7-111-11111-1")
                        .authorId(1L)
                        .publisherId(1L)
                        .publicationDate(LocalDate.now())
                        .price(new BigDecimal("50.00"))
                        .build()
        );

        // 执行测试
        List<BookDTO> result = bookSortingContext.executeSort("Title", books);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("单本书籍", result.get(0).getTitle());
    }
} 