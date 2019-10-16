package com.cskaoyan.cinema.rest.common.exception;

import com.cskaoyan.cinema.core.exception.ServiceExceptionEnum;

public enum OrderExceptionEnum implements ServiceExceptionEnum {
    //订单支付失败或
    PAYMENT_FAILED(1, "订单支付失败，请稍后重试");

    OrderExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private Integer code;

    private String message;

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
