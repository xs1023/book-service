package com.book.mapper;

import com.book.dto.BookQueryDTO;
import com.book.po.Book;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BooksMapper {
    void insert(Book book);

    void update(Book book);

    void delete(Long id);

    Book findById(Long id);

    Book findByIsbn(String isbn);

    boolean existsByIsbn(String isbn);

    List<Book> findAll();

    List<Book> findByPage(Map<String, Object> params);

    int countByCondition(Map<String, Object> params);

    List<Book> findByCondition(@Param("params") BookQueryDTO params);

    int countByCondition(@Param("params") BookQueryDTO params);

}
