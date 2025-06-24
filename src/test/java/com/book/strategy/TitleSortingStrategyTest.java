package com.book.strategy;

import com.book.dto.BookDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TitleSortingStrategyTest {

    private TitleSortingStrategy titleSortingStrategy;

    @BeforeEach
    void setUp() {
        titleSortingStrategy = new TitleSortingStrategy();
    }

    @Test
    void testSortWithMultipleBooks() {
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
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Java编程思想", result.get(0).getTitle());
        assertEquals("三体", result.get(1).getTitle());
        assertEquals("算法导论", result.get(2).getTitle());
    }

    @Test
    void testSortWithEmptyList() {
        // 准备空列表
        List<BookDTO> books = Collections.emptyList();

        // 执行测试
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testSortWithSingleBook() {
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
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("单本书籍", result.get(0).getTitle());
    }

    @Test
    void testSortWithNullTitles() {
        // 准备包含null标题的书籍列表
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder()
                        .title(null)
                        .isbn("978-7-111-11111-1")
                        .build(),
                BookDTO.builder()
                        .title("有标题的书籍")
                        .isbn("978-7-111-11111-2")
                        .build()
        );

        // 执行测试
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertEquals(2, result.size());
        // null标题应该排在前面
        assertNull(result.get(0).getTitle());
        assertEquals("有标题的书籍", result.get(1).getTitle());
    }

    @Test
    void testSortWithDuplicateTitles() {
        // 准备包含重复标题的书籍列表
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder()
                        .title("相同标题")
                        .isbn("978-7-111-11111-1")
                        .authorId(1L)
                        .build(),
                BookDTO.builder()
                        .title("相同标题")
                        .isbn("978-7-111-11111-2")
                        .authorId(2L)
                        .build(),
                BookDTO.builder()
                        .title("不同标题")
                        .isbn("978-7-111-11111-3")
                        .authorId(3L)
                        .build()
        );

        // 执行测试
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("不同标题", result.get(0).getTitle());
        assertEquals("相同标题", result.get(1).getTitle());
        assertEquals("相同标题", result.get(2).getTitle());
    }

    @Test
    void testSortWithChineseAndEnglishTitles() {
        // 准备中英文混合标题的书籍列表
        List<BookDTO> books = Arrays.asList(
                BookDTO.builder()
                        .title("三体")
                        .isbn("978-7-111-11111-1")
                        .build(),
                BookDTO.builder()
                        .title("Java Programming")
                        .isbn("978-7-111-11111-2")
                        .build(),
                BookDTO.builder()
                        .title("算法导论")
                        .isbn("978-7-111-11111-3")
                        .build()
        );

        // 执行测试
        List<BookDTO> result = titleSortingStrategy.sort(books);

        // 验证结果
        assertNotNull(result);
        assertEquals(3, result.size());
        assertEquals("Java Programming", result.get(0).getTitle());
        assertEquals("三体", result.get(1).getTitle());
        assertEquals("算法导论", result.get(2).getTitle());
    }

    @Test
    void testSortWithNullList() {
        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> titleSortingStrategy.sort(null));
    }
} 