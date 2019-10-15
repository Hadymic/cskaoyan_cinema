package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class BoxRanking implements Serializable {
    private static final long serialVersionUID = 2550321904580844976L;

    private Integer filmId;
    private String imgAddress;
    private String filmName;
    private Integer boxNum;
}
