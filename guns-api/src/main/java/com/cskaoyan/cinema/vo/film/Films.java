package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class Films implements Serializable {
    private static final long serialVersionUID = 6894410132827857956L;

    private Integer filmNum;
    private List<FilmInfo> filmInfo;
}
