package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class FilmInfo implements Serializable {

    private static final long serialVersionUID = 5560828749310201245L;

    private Integer filmId;
    private Integer filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
    private Integer boxNum;
    private Integer expectNum;
    private String filmCats;
    private Integer filmLength;
    private String score;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date showTime;
}
