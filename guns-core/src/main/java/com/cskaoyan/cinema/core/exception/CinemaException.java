package com.cskaoyan.cinema.core.exception;

/**
 * 自定义全局业务异常返回类
 *
 * @author hadymic
 */
public class CinemaException extends RuntimeException {
    private Integer code;

    private String message;

    public CinemaException(ServiceExceptionEnum serviceExceptionEnum) {
        this.code = serviceExceptionEnum.getCode();
        this.message = serviceExceptionEnum.getMessage();
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
