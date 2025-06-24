package com.book.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.Date;

@Data
public class BookQueryDTO {

    private String title;

    private String isbn;

    private String author;

    private String publisher;

    private Long categoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDateStart;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date publishDateEnd;

    private Integer status;

    @Min(value = 0, message = "价格不能为负数")
    private Double minPrice;

    @Min(value = 0, message = "价格不能为负数")
    private Double maxPrice;

    private String sortBy = "publishDate";

    private String sortDirection = "desc";

    @Min(value = 1, message = "页码不能小于1")
    private Integer pageNum = 1;

    @Min(value = 1, message = "每页数量不能小于1")
    @Max(value = 100, message = "每页数量不能大于100")
    private Integer pageSize = 10;

    /**
     * 计算分页偏移量
     */
    public Integer getOffset() {
        return (pageNum - 1) * pageSize;
    }
}
