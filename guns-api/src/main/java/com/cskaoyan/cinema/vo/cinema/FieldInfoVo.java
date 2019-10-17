package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldInfoVo implements Serializable {
    private static final long serialVersionUID = 5075787183682259213L;
    private CinemaInfoVo cinemaInfo;
    private FilmInfoVo filmInfo;
    private HallInfoVo hallInfo;

    public CinemaInfoVo getCinemaInfo() {
        return cinemaInfo;
    }

    public FieldInfoVo setCinemaInfo(CinemaInfoVo cinemaInfo) {
        this.cinemaInfo = cinemaInfo;
        return this;
    }

    public FilmInfoVo getFilmInfo() {
        return filmInfo;
    }

    public FieldInfoVo setFilmInfo(FilmInfoVo filmInfo) {
        this.filmInfo = filmInfo;
        return this;
    }

    public HallInfoVo getHallInfo() {
        return hallInfo;
    }

    public FieldInfoVo setHallInfo(HallInfoVo hallInfo) {
        this.hallInfo = hallInfo;
        return this;
    }
}
