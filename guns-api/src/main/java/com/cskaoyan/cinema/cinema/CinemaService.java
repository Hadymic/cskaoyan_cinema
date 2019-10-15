package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.CinemaQueryVo;
import com.cskaoyan.cinema.vo.CinemaVo;

import java.util.List;

public interface CinemaService {
    List<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);
}
