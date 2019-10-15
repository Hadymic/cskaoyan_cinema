package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Top implements Serializable {

    private static final long serialVersionUID = -2748567782920174098L;
    private Integer filmId;
    private String imgAddress;
    private String filmName;
    private String score;
}
