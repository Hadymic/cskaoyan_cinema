package com.cskaoyan.cinema.cinema;


import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;

import com.cskaoyan.cinema.vo.ConditionVo;


import java.util.List;

public interface CinemaService {
    List<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId);
}
