package com.cskaoyan.cinema.rest.common.persistence.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class HotFilms implements Serializable {
    private static final long serialVersionUID = 6894410132827857956L;

    private Integer filmNum;
    private List<HotFilmInfo> filmInfo;
}
