package com.cskaoyan.cinema.vo.order;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Zeng-jz
 */
@Data
public class OrderVo implements Serializable {

    private static final long serialVersionUID = -3072861182004069823L;
    private String orderId;
    private String filmName;
    @JsonFormat(pattern = "yyyy年MM月dd日 HH:mm")
    private Date fieldTime;
    private String cinemaName;
    private String seatsName;
    private Double orderPrice;
    private String orderStatus;
    private String orderTimestamp;
}
