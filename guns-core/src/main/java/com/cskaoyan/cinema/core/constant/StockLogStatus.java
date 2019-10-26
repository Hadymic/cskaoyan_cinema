package com.cskaoyan.cinema.core.constant;

/**
 * StockLog表status字段枚举类
 */
public enum StockLogStatus {
    //初始化状态
    INIT(0, "初始"),
    //成功状态
    SUCCESS(1, "成功"),
    //失败状态
    FAIL(2, "失败");

    private int code;
    private String status;

    StockLogStatus(int code, String status) {
        this.code = code;
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
