package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SoonFilms implements Serializable {

    private static final long serialVersionUID = 3842777403035527907L;
    private Integer filmNum;
    private List<HotFilmInfo> filmInfo;
}
