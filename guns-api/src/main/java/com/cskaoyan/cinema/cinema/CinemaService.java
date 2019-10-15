package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;

import java.util.List;

public interface CinemaService {
    List<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);
}
