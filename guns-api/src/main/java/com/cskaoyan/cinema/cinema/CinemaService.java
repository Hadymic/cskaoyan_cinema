package com.cskaoyan.cinema.cinema;

import java.util.List;

public interface CinemaService {
    List<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);
}
