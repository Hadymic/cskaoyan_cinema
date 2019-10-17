package com.cskaoyan.cinema.vo.order;

import lombok.Data;

import java.io.Serializable;

@Data
public class OrderMsgVo implements Serializable {
    private static final long serialVersionUID = -4700351647070687494L;
    private String cinemaName;
    private String fieldTime;
    private String orderId;
    private String orderPrice;
    private String filmName;
    private String orderTimestamp;
    private String seatsName;
}
