package com.cskaoyan.cinema.vo.promo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 获取秒杀活动列表的返回vo
 *
 * @author hadymic
 */
@Data
public class GetPromoVo implements Serializable {
    private static final long serialVersionUID = -4729810336907254683L;

    private String cinemaAddress;
    private Integer cinemaId;
    private String cinemaName;
    private String description;
    private Date endTime;
    private String imgAddress;
    private BigDecimal price;
    private Date startTime;
    private Integer status;
    private Integer stock;
    private Integer uuid;

    public GetPromoVo(String cinemaAddress, Integer cinemaId, String cinemaName, String description, Date endTime, String imgAddress, BigDecimal price, Date startTime, Integer status, Integer stock, Integer uuid) {
        this.cinemaAddress = cinemaAddress;
        this.cinemaId = cinemaId;
        this.cinemaName = cinemaName;
        this.description = description;
        this.endTime = endTime;
        this.imgAddress = imgAddress;
        this.price = price;
        this.startTime = startTime;
        this.status = status;
        this.stock = stock;
        this.uuid = uuid;
    }
}
