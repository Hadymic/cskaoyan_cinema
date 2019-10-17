package com.cskaoyan.cinema.vo.film;

import com.cskaoyan.cinema.rest.common.persistence.vo.ImgVO;
import lombok.Data;

import java.io.Serializable;

@Data
public class FilmInfoVO implements Serializable {
    private static final long serialVersionUID = 2660219463362223411L;

    private String filmName;
    private String filmEnName;
    private String imgAddress;
    private String score;
    private String scoreNum;
    private String totalBox;
    private String info01;
    private String info02;
    private String info03;
    private FilmActors info04;
    private Integer filmId;
}

