package com.cskaoyan.cinema.rest.common.exception;

import com.cskaoyan.cinema.core.exception.ServiceExceptionEnum;

public enum PromoExceptionEnum implements ServiceExceptionEnum {
    FILM_NOT_FOUND(1,"没有找到搜索内容"),
    FAIL_TO_ORDER(1,"下单失败，请您重新尝试哦~")
    ;


    PromoExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }
}
