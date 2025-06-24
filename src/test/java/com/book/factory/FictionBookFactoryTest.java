package com.book.factory;

import com.book.dto.BookDTO;
import com.book.po.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FictionBookFactoryTest {

    private FictionBookFactory fictionBookFactory;

    @BeforeEach
    void setUp() {
        fictionBookFactory = new FictionBookFactory();
    }

    @Test
    void testCreateBook() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("三体")
                .isbn("978-7-5366-9293-0")
                .authorId(2L)
                .authorName("刘慈欣")
                .publisherId(2L)
                .publisherName("重庆出版社")
                .publicationDate(LocalDate.of(2008, 1, 1))
                .price(new BigDecimal("68.00"))
                .stockQuantity(50)
                .description("科幻小说经典之作")
                .build();

        // 执行测试
        Book result = fictionBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("FICTION", result.getCategory());
        assertNotNull(result.getTitle());
        assertNotNull(result.getIsbn());
    }

    @Test
    void testCreateBookWithMinimalData() {
        // 准备最小测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("测试小说")
                .isbn("978-7-111-11111-1")
                .authorId(1L)
                .publisherId(1L)
                .publicationDate(LocalDate.now())
                .price(new BigDecimal("20.00"))
                .build();

        // 执行测试
        Book result = fictionBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("FICTION", result.getCategory());
    }

    @Test
    void testCreateBookWithNullBookDTO() {
        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> fictionBookFactory.createBook(null));
    }

    @Test
    void testCreateBookWithEmptyBookDTO() {
        // 准备空的BookDTO
        BookDTO bookDTO = new BookDTO();

        // 执行测试
        Book result = fictionBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("FICTION", result.getCategory());
    }
} 