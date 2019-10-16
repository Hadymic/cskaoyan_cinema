package com.cskaoyan.cinema.vo.order;

import lombok.Data;

@Data
public class PayInfoVO {
    private String orderId;

    private String QRCodeAddress;

    public PayInfoVO() {
    }

    public PayInfoVO(String orderId, String QRCodeAddress) {
        this.orderId = orderId;
        this.QRCodeAddress = QRCodeAddress;
    }
}
