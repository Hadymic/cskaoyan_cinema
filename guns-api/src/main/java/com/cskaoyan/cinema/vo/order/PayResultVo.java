package com.cskaoyan.cinema.vo.order;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付结果返回vo
 *
 * @author hadymic
 */
@Data
public class PayResultVo implements Serializable {

    private static final long serialVersionUID = 3147474455503401907L;

    private String orderId;
    private Integer orderStatus;
    private String orderMsg;

    public PayResultVo() {
    }

    public PayResultVo(String orderId, Integer orderStatus, String orderMsg) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.orderMsg = orderMsg;
    }
}
