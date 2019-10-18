package com.cskaoyan.cinema.vo.promo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GetPromoVo implements Serializable {
    private static final long serialVersionUID = -4174457324796930765L;

    private String cinemaAddress;
    private Integer cinemaId;
    private String cinemaName;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;
    private String imgAddress;
    private Integer price;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    private Integer status;
    private Integer stock;
    private Integer uuid;

}
