package com.deviation.blog.common;

public class BusinessException extends RuntimeException {

    private String code;
    private String message;


    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ApiResultStatus apiResultStatus) {
        this.code = apiResultStatus.getCode();
        this.message = apiResultStatus.getMessage();
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }


    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}