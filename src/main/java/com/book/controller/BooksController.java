package com.book.controller;


import com.book.dto.BookDTO;
import com.book.dto.BookQueryDTO;
import com.book.dto.PageResult;
import com.book.dto.Result;
import com.book.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BooksController {
    private final BookService bookService;

    @GetMapping
    public Result<List<BookDTO>> getAllBooks() {
        List<BookDTO> books = bookService.getAllBooks();
        return Result.success("获取所有图书成功", books);
    }

    @GetMapping("/{id}")
    public Result<BookDTO> getBookById(@PathVariable Long id) {
        BookDTO book = bookService.getBookById(id);
        return Result.success("获取图书成功", book);
    }

    @PostMapping
    public Result<BookDTO> createBook(@Valid @RequestBody BookDTO book) {
        BookDTO createdBook = bookService.createBook(book);
        return Result.success("创建图书成功", createdBook);
    }

    @PutMapping("/{id}")
    public Result<BookDTO> updateBook(@PathVariable Long id, @Valid @RequestBody BookDTO book) {
        BookDTO updatedBook = bookService.updateBook(id, book);
        return Result.success("更新图书成功", updatedBook);
    }

    @DeleteMapping("/{id}")
    public Result<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return Result.success("删除图书成功");
    }

    @GetMapping("/query")
    public Result<PageResult<BookDTO>> queryBooks(@Valid BookQueryDTO queryDTO) {
        PageResult<BookDTO> pageResult = bookService.listBooks(queryDTO);
        return Result.success("查询图书成功", pageResult);
    }
}