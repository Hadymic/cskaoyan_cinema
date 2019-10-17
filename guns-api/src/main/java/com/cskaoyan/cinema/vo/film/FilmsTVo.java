package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmsTVo implements Serializable {

    //票房
    private Integer boxNum;
    //预售数量
    private Integer expectNum;
    private Integer filmId;
    private String filmName;
    private Integer filmScore;
    private String imgAddress;
    private Integer score;
    
    private Date showTime;
}
