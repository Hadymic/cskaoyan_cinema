package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class HotFilmInfo implements Serializable {

    private static final long serialVersionUID = 5560828749310201245L;

    private Integer filmId;
    private Integer filmType;
    private String imgAddress;
    private String filmName;
    private String filmScore;
}
