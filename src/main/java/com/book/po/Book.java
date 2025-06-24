package com.book.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book implements Serializable {

    private static final long serialVersionUID = 1038261860150918056L;
    private Long id;

    @NotBlank(message = "书名不能为空")
    private String title;

    @NotBlank(message = "作者不能为空")
    private String author;

    @NotNull(message = "出版日期不能为空")
    private Date publicationDate;

    @NotBlank(message = "书号不能为空")
    private String isbn;

    @PositiveOrZero(message = "数量不能为空")
    private Integer quantity;
    private String category;
}

