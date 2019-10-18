package com.cskaoyan.cinema.rest.common.exception;


import com.cskaoyan.cinema.core.exception.ServiceExceptionEnum;

/**
 * @author jszza
 */

public enum PromoExceptionEnum implements ServiceExceptionEnum {
    /**
     * 购买数量大于库存
     */
    AMOUNT_ERROR(1, "购买数量大于库存，请重新选择数量！"),

    /**
     * 库存为零
     */
    STOCK_ERROR(1, "库存已空！");

    private Integer code;

    private String message;

    PromoExceptionEnum(int code, String message) {
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
