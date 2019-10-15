package com.cskaoyan.cinema.rest.common.persistence.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class SoonFilmInfo implements Serializable {

    private static final long serialVersionUID = 3083104026458861357L;
    private Integer filmId;
    private Integer filmType;
    private String imgAddress;
    private String filmName;
    private Integer expectNum;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date showTime;
}
