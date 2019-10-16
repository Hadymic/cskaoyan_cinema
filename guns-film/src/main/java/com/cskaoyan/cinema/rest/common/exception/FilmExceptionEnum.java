package com.cskaoyan.cinema.rest.common.exception;


import com.cskaoyan.cinema.core.exception.ServiceExceptionEnum;

/**
 *
 * @author jszza
 */

public enum FilmExceptionEnum implements ServiceExceptionEnum {
    /**
     * 错误的请求
     */
    VAR_REQUEST_NULL(400, "传入参数有误"),

    /**
     * 资源无法加载
     */
    FILM_NOT_FOUND(400,"FILM_NOT_FOUND!")
    ;

    private Integer code;

    private String message;

    FilmExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }
    public void setCode(Integer code) {
        this.code = code;
    }
    @Override
    public String getMessage() {
        return null;
    }
    public void setMessage(String message) {
        this.message = message;
    }
}
