package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import com.cskaoyan.cinema.vo.cinema.CinemaMsgVo;
import com.cskaoyan.cinema.vo.cinema.ListBean;

public interface CinemaService {
    ListBean<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    CinemaMsgVo queryCinemaMsg(String cinemaId);
}
