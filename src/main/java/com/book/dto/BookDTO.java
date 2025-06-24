package com.book.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {

    private Long id;

    @NotBlank(message = "书名不能为空")
    @Size(min = 1, max = 100, message = "书名长度必须在1-100个字符之间")
    private String title;

    @NotBlank(message = "ISBN不能为空")
    @Pattern(regexp = "^(?:ISBN(?:-1[03])?:? )?(?=[0-9X]{10}$|(?=(?:[0-9]+[- ]){3})[- 0-9X]{13}$|97[89][0-9]{10}$|(?=(?:[0-9]+[- ]){4})[- 0-9]{17}$)(?:97[89][- ]?)?[0-9]{1,5}[- ]?[0-9]+[- ]?[0-9]+[- ]?[0-9X]$",
            message = "ISBN格式不正确")
    private String isbn;

    @NotNull(message = "作者ID不能为空")
    private Long authorId;

    private String authorName;

    @NotNull(message = "出版社ID不能为空")
    private Long publisherId;

    private String publisherName;

    @NotNull(message = "出版日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate publicationDate;

    @NotNull(message = "价格不能为空")
    @DecimalMin(value = "0.0", inclusive = false, message = "价格必须大于0")
    private BigDecimal price;

    @Min(value = 0, message = "库存数量不能小于0")
    private Integer stockQuantity;

    private String coverImageUrl;

    private String description;

    private Integer status;
}
