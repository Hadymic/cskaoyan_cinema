package com.cskaoyan.cinema.vo.film;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class IndexVO implements Serializable {
    private static final long serialVersionUID = -4899683492034322201L;

    private List<Banner> banners;
    private Films hotFilms;
    private Films soonFilms;
    private List<FilmInfo> boxRanking;
    private List<FilmInfo> expectRankings;
    private List<FilmInfo> top100;
}
