package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
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
    @JsonFormat(pattern = "MM月dd号 HH:mm")
    private Date fieldTime;
    private String cinemaName;
    private String seatsName;
    private Double orderPrice;
    private String orderStatus;
}
