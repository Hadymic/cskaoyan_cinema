package com.cskaoyan.cinema.vo.cinema;

import lombok.Data;

import java.io.Serializable;

@Data
public class FieldInfoVo implements Serializable {
    private static final long serialVersionUID = 5075787183682259213L;
    private CinemaInfoVo cinemaInfoVo;
    private FilmInfoVo filmInfoVo;
    private HallInfoVo hallInfoVo;

    public CinemaInfoVo getCinemaInfoVo() {
        return cinemaInfoVo;
    }

    public FieldInfoVo setCinemaInfoVo(CinemaInfoVo cinemaInfoVo) {
        this.cinemaInfoVo = cinemaInfoVo;
        return this;
    }

    public FilmInfoVo getFilmInfoVo() {
        return filmInfoVo;
    }

    public FieldInfoVo setFilmInfoVo(FilmInfoVo filmInfoVo) {
        this.filmInfoVo = filmInfoVo;
        return this;
    }

    public HallInfoVo getHallInfoVo() {
        return hallInfoVo;
    }

    public FieldInfoVo setHallInfoVo(HallInfoVo hallInfoVo) {
        this.hallInfoVo = hallInfoVo;
        return this;
    }
}
