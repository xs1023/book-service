package com.book.exception;

import com.book.constant.ErrorCode;
import lombok.Getter;

/**
 * 业务异常类
 */
@Getter
public class BusinessException extends RuntimeException {
    
    /**
     * 错误码
     */
    private final Integer code;
    
    /**
     * 错误消息
     */
    private final String message;
    
    public BusinessException(String message) {
        super(message);
        this.code = ErrorCode.BAD_REQUEST;
        this.message = message;
    }
    
    public BusinessException(Integer code, String message) {
        super(message);
        this.code = code;
        this.message = message;
    }
    
    public BusinessException(String message, Throwable cause) {
        super(message, cause);
        this.code = ErrorCode.BAD_REQUEST;
        this.message = message;
    }
    
    public BusinessException(Integer code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }
    
    /**
     * 创建资源不存在异常
     */
    public static BusinessException resourceNotFound(String resourceName, Object identifier) {
        return new BusinessException(ErrorCode.NOT_FOUND, 
            resourceName + "不存在，ID: " + identifier);
    }
    
    /**
     * 创建资源已存在异常
     */
    public static BusinessException resourceExists(String resourceName, Object identifier) {
        return new BusinessException(ErrorCode.CONFLICT, 
            resourceName + "已存在，ID: " + identifier);
    }
    
    /**
     * 创建参数错误异常
     */
    public static BusinessException invalidParameter(String parameterName, String reason) {
        return new BusinessException(ErrorCode.BAD_REQUEST, 
            "参数错误: " + parameterName + " - " + reason);
    }
    
    /**
     * 创建操作失败异常
     */
    public static BusinessException operationFailed(String operation, String reason) {
        return new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, 
            "操作失败: " + operation + " - " + reason);
    }
    
    /**
     * 创建图书不存在异常
     */
    public static BusinessException bookNotFound(Long bookId) {
        return new BusinessException(ErrorCode.BOOK_NOT_FOUND, 
            "图书不存在，ID: " + bookId);
    }
    
    /**
     * 创建图书已存在异常
     */
    public static BusinessException bookExists(String bookName) {
        return new BusinessException(ErrorCode.BOOK_ALREADY_EXISTS, 
            "图书已存在，书名: " + bookName);
    }
    
    /**
     * 创建图书数据无效异常
     */
    public static BusinessException invalidBookData(String reason) {
        return new BusinessException(ErrorCode.INVALID_BOOK_DATA, 
            "图书数据无效: " + reason);
    }
    
    /**
     * 创建图书删除失败异常
     */
    public static BusinessException bookDeleteFailed(Long bookId, String reason) {
        return new BusinessException(ErrorCode.BOOK_DELETE_FAILED, 
            "删除图书失败，ID: " + bookId + "，原因: " + reason);
    }
    
    /**
     * 创建图书更新失败异常
     */
    public static BusinessException bookUpdateFailed(Long bookId, String reason) {
        return new BusinessException(ErrorCode.BOOK_UPDATE_FAILED, 
            "更新图书失败，ID: " + bookId + "，原因: " + reason);
    }
    
    /**
     * 创建图书创建失败异常
     */
    public static BusinessException bookCreateFailed(String reason) {
        return new BusinessException(ErrorCode.BOOK_CREATE_FAILED, 
            "创建图书失败，原因: " + reason);
    }
} 