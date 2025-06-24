package com.book.integration;

import com.book.dto.BookDTO;
import com.book.enm.BookType;
import com.book.factory.BookFactoryContext;
import com.book.factory.EducationalBookFactory;
import com.book.factory.FictionBookFactory;
import com.book.po.Book;
import com.book.service.context.BookSortingContext;
import com.book.strategy.TitleSortingStrategy;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FactoryAndStrategyIntegrationTest {

    private BookFactoryContext bookFactoryContext;
    private BookSortingContext bookSortingContext;

    @BeforeEach
    void setUp() {
        // 初始化工厂上下文
        List<com.book.factory.BookFactory> factoryList = Arrays.asList(
                new EducationalBookFactory(),
                new FictionBookFactory()
        );
        bookFactoryContext = new BookFactoryContext(factoryList);

        // 初始化策略上下文
        List<com.book.strategy.SortingStrategy> strategyList = Arrays.asList(
                new TitleSortingStrategy()
        );
        bookSortingContext = new BookSortingContext(strategyList);
    }

    @Test
    void testCreateAndSortBooks() {
        // 准备测试数据
        List<BookDTO> bookDTOs = Arrays.asList(
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

        // 使用工厂模式创建不同类型的书籍
        Book fictionBook = bookFactoryContext.createBook(BookType.FICTION, bookDTOs.get(0));
        Book educationalBook1 = bookFactoryContext.createBook(BookType.EDUCATIONAL, bookDTOs.get(1));
        Book educationalBook2 = bookFactoryContext.createBook(BookType.EDUCATIONAL, bookDTOs.get(2));

        // 验证工厂创建的结果
        assertEquals("FICTION", fictionBook.getCategory());
        assertEquals("EDUCATIONAL", educationalBook1.getCategory());
        assertEquals("EDUCATIONAL", educationalBook2.getCategory());

        // 使用策略模式对书籍列表进行排序
        List<BookDTO> sortedBooks = bookSortingContext.executeSort("Title", bookDTOs);

        // 验证排序结果
        assertNotNull(sortedBooks);
        assertEquals(3, sortedBooks.size());
        assertEquals("Java编程思想", sortedBooks.get(0).getTitle());
        assertEquals("三体", sortedBooks.get(1).getTitle());
        assertEquals("算法导论", sortedBooks.get(2).getTitle());
    }

    @Test
    void testCreateMixedBookTypesAndSort() {
        // 准备混合类型的书籍数据
        List<BookDTO> fictionBooks = Arrays.asList(
                BookDTO.builder()
                        .title("三体")
                        .isbn("978-7-5366-9293-0")
                        .authorId(1L)
                        .publisherId(1L)
                        .publicationDate(LocalDate.of(2008, 1, 1))
                        .price(new BigDecimal("68.00"))
                        .build(),
                BookDTO.builder()
                        .title("球状闪电")
                        .isbn("978-7-5366-9294-0")
                        .authorId(1L)
                        .publisherId(1L)
                        .publicationDate(LocalDate.of(2005, 1, 1))
                        .price(new BigDecimal("45.00"))
                        .build()
        );

        List<BookDTO> educationalBooks = Arrays.asList(
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

        // 使用工厂模式创建不同类型的书籍
        for (BookDTO fictionDTO : fictionBooks) {
            Book book = bookFactoryContext.createBook(BookType.FICTION, fictionDTO);
            assertEquals("FICTION", book.getCategory());
        }

        for (BookDTO educationalDTO : educationalBooks) {
            Book book = bookFactoryContext.createBook(BookType.EDUCATIONAL, educationalDTO);
            assertEquals("EDUCATIONAL", book.getCategory());
        }

        // 合并所有书籍并排序
        List<BookDTO> allBooks = Arrays.asList(
                fictionBooks.get(0), fictionBooks.get(1),
                educationalBooks.get(0), educationalBooks.get(1)
        );

        List<BookDTO> sortedBooks = bookSortingContext.executeSort("Title", allBooks);

        // 验证排序结果
        assertNotNull(sortedBooks);
        assertEquals(4, sortedBooks.size());
        assertEquals("Java编程思想", sortedBooks.get(0).getTitle());
        assertEquals("三体", sortedBooks.get(1).getTitle());
        assertEquals("球状闪电", sortedBooks.get(2).getTitle());
        assertEquals("算法导论", sortedBooks.get(3).getTitle());
    }

    @Test
    void testErrorHandling() {
        // 测试工厂模式的错误处理
        BookDTO bookDTO = BookDTO.builder()
                .title("测试书籍")
                .isbn("978-7-111-11111-1")
                .build();

        // 测试不支持的书籍类型
        IllegalArgumentException factoryException = assertThrows(
                IllegalArgumentException.class,
                () -> bookFactoryContext.createBook(BookType.CHILDREN, bookDTO)
        );
        assertEquals("Unsupported book type: CHILDREN", factoryException.getMessage());

        // 测试策略模式的错误处理
        List<BookDTO> books = Arrays.asList(bookDTO);
        IllegalArgumentException strategyException = assertThrows(
                IllegalArgumentException.class,
                () -> bookSortingContext.executeSort("Unknown", books)
        );
        assertEquals("Unknown sorting strategy: Unknown", strategyException.getMessage());
    }

    @Test
    void testPerformanceWithLargeDataset() {
        // 创建大量测试数据
        List<BookDTO> largeBookList = Arrays.asList(
                BookDTO.builder().title("A").isbn("978-1").build(),
                BookDTO.builder().title("Z").isbn("978-2").build(),
                BookDTO.builder().title("M").isbn("978-3").build(),
                BookDTO.builder().title("B").isbn("978-4").build(),
                BookDTO.builder().title("Y").isbn("978-5").build(),
                BookDTO.builder().title("N").isbn("978-6").build(),
                BookDTO.builder().title("C").isbn("978-7").build(),
                BookDTO.builder().title("X").isbn("978-8").build(),
                BookDTO.builder().title("O").isbn("978-9").build(),
                BookDTO.builder().title("D").isbn("978-10").build()
        );

        // 测试排序性能
        long startTime = System.currentTimeMillis();
        List<BookDTO> sortedBooks = bookSortingContext.executeSort("Title", largeBookList);
        long endTime = System.currentTimeMillis();

        // 验证排序结果
        assertNotNull(sortedBooks);
        assertEquals(10, sortedBooks.size());
        assertEquals("A", sortedBooks.get(0).getTitle());
        assertEquals("Z", sortedBooks.get(9).getTitle());

        // 验证性能（排序应该在合理时间内完成）
        long duration = endTime - startTime;
        assertTrue(duration < 1000, "排序耗时过长: " + duration + "ms");
    }
} 