package com.cskaoyan.cinema.vo.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class PayInfoVO implements Serializable {
    private static final long serialVersionUID = 1415182096237092387L;
    private String orderId;

    private String QRCodeAddress;

    public PayInfoVO() {
    }

    public PayInfoVO(String orderId, String QRCodeAddress) {
        this.orderId = orderId;
        this.QRCodeAddress = QRCodeAddress;
    }
}
