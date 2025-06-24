package com.book.factory;

import com.book.dto.BookDTO;
import com.book.po.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EducationalBookFactoryTest {

    private EducationalBookFactory educationalBookFactory;

    @BeforeEach
    void setUp() {
        educationalBookFactory = new EducationalBookFactory();
    }

    @Test
    void testCreateBook() {
        // 准备测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("Java编程思想")
                .isbn("978-7-111-54742-6")
                .authorId(1L)
                .authorName("Bruce Eckel")
                .publisherId(1L)
                .publisherName("机械工业出版社")
                .publicationDate(LocalDate.of(2020, 1, 1))
                .price(new BigDecimal("89.00"))
                .stockQuantity(100)
                .description("Java编程的经典教材")
                .build();

        // 执行测试
        Book result = educationalBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("EDUCATIONAL", result.getCategory());
        assertNotNull(result.getTitle());
        assertNotNull(result.getIsbn());
    }

    @Test
    void testCreateBookWithMinimalData() {
        // 准备最小测试数据
        BookDTO bookDTO = BookDTO.builder()
                .title("测试教育书籍")
                .isbn("978-7-111-11111-1")
                .authorId(1L)
                .publisherId(1L)
                .publicationDate(LocalDate.now())
                .price(new BigDecimal("10.00"))
                .build();

        // 执行测试
        Book result = educationalBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("EDUCATIONAL", result.getCategory());
    }

    @Test
    void testCreateBookWithNullBookDTO() {
        // 执行测试并验证异常
        assertThrows(NullPointerException.class, () -> educationalBookFactory.createBook(null));
    }

    @Test
    void testCreateBookWithEmptyBookDTO() {
        // 准备空的BookDTO
        BookDTO bookDTO = new BookDTO();

        // 执行测试
        Book result = educationalBookFactory.createBook(bookDTO);

        // 验证结果
        assertNotNull(result);
        assertEquals("EDUCATIONAL", result.getCategory());
    }
} 