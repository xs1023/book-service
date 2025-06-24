package com.book.dto;

import lombok.Data;

import java.util.Collections;
import java.util.List;

/**
 * 分页结果封装类
 *
 * @param <T> 数据类型
 */
@Data
public class PageResult<T> {

    private List<T> records;

    private long total;

    private int pageNum;

    private int pageSize;

    private int pages;

    private boolean hasPrevious;

    private boolean hasNext;

    /**
     * 空分页结果构造方法
     */
    public PageResult() {
        this.records = Collections.emptyList();
        this.total = 0L;
        this.pageNum = 1;
        this.pageSize = 10;
        this.pages = 0;
        this.hasPrevious = false;
        this.hasNext = false;
    }

    /**
     * 分页结果构造方法
     *
     * @param records  当前页数据
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     */
    public PageResult(List<T> records, long total, int pageNum, int pageSize) {
        this.records = records;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
        this.pages = (int) Math.ceil((double) total / pageSize);
        this.hasPrevious = pageNum > 1;
        this.hasNext = pageNum < pages;
    }

    /**
     * 返回空分页结果
     *
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @param <T>      数据类型
     * @return 空分页结果
     */
    public static <T> PageResult<T> empty(int pageNum, int pageSize) {
        PageResult<T> pageResult = new PageResult<>();
        pageResult.setPageNum(pageNum);
        pageResult.setPageSize(pageSize);
        return pageResult;
    }

    /**
     * 构建分页结果
     *
     * @param records  当前页数据
     * @param total    总记录数
     * @param pageNum  当前页码
     * @param pageSize 每页数量
     * @param <T>      数据类型
     * @return 分页结果
     */
    public static <T> PageResult<T> of(List<T> records, long total, int pageNum, int pageSize) {
        return new PageResult<>(records, total, pageNum, pageSize);
    }
}
