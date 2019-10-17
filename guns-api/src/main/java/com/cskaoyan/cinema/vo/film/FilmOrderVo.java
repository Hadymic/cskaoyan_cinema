package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmOrderVo implements Serializable {
    private static final long serialVersionUID = 7326288144769390452L;

    private Integer uuid;

    private String filmName;

    private Integer filmType;

    private String imgAddress;

    private String filmScore;

    private Integer filmPreSaleNum;

    private Integer filmBoxOffice;

    private Integer filmSource;

    private String filmCats;

    private Integer filmArea;

    private Integer filmDate;

    private Date filmTime;

    private Integer filmStatus;
}
