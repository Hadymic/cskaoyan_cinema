package com.cskaoyan.cinema.vo.film;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmsTVo implements Serializable {

    private static final long serialVersionUID = 804723270290104793L;
    //票房
    private Integer boxNum;
    //预售数量
    private Integer expectNum;
    private Integer filmId;
    private String filmName;
    private String filmScore;
    private String imgAddress;
    private Integer filmType;
    private String score;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date showTime;
}
