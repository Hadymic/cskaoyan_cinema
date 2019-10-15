package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class ImgVO implements Serializable {
    private static final long serialVersionUID = 6755705442401710638L;
    private String mainImg;
    private String img01;
    private String img02;
    private String img03;
    private String img04;
}
