package com.book.constant;

/**
 * 错误码常量类
 */
public class ErrorCode {
    
    // HTTP状态码
    public static final int OK = 200;
    public static final int BAD_REQUEST = 400;
    public static final int UNAUTHORIZED = 401;
    public static final int FORBIDDEN = 403;
    public static final int NOT_FOUND = 404;
    public static final int METHOD_NOT_ALLOWED = 405;
    public static final int CONFLICT = 409;
    public static final int INTERNAL_SERVER_ERROR = 500;
    
    // 业务错误码
    public static final int BOOK_NOT_FOUND = 1001;
    public static final int BOOK_ALREADY_EXISTS = 1002;
    public static final int INVALID_BOOK_DATA = 1003;
    public static final int BOOK_DELETE_FAILED = 1004;
    public static final int BOOK_UPDATE_FAILED = 1005;
    public static final int BOOK_CREATE_FAILED = 1006;
    
    // 错误消息
    public static final String SUCCESS_MESSAGE = "操作成功";
    public static final String FAILED_MESSAGE = "操作失败";
    public static final String SYSTEM_ERROR_MESSAGE = "系统异常，请联系管理员";
    public static final String PARAMETER_ERROR_MESSAGE = "参数错误";
    public static final String RESOURCE_NOT_FOUND_MESSAGE = "资源不存在";
    public static final String RESOURCE_EXISTS_MESSAGE = "资源已存在";
    public static final String DATABASE_ERROR_MESSAGE = "数据库操作异常";
    public static final String VALIDATION_ERROR_MESSAGE = "数据校验失败";
} 