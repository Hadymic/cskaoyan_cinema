package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class CinemaVo implements Serializable {
    private static final long serialVersionUID = -7912924984186708341L;
    private Integer uuid;
    private String cinemaName;
    private String cinemaAddress;
    private Integer minimumPrice;

    public CinemaVo(Integer uuid, String cinemaName, String cinemaAddress, Integer minimumPrice) {
        this.uuid = uuid;
        this.cinemaName = cinemaName;
        this.cinemaAddress = cinemaAddress;
        this.minimumPrice = minimumPrice;
    }

    public CinemaVo() {
    }
}
