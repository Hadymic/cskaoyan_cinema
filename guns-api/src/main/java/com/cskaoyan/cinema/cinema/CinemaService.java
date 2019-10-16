package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.CinemaMsgVo;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import com.cskaoyan.cinema.vo.cinema.ListBean;

import java.util.List;

public interface CinemaService {

    ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId);
    ListBean<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    CinemaMsgVo queryCinemaMsg(String cinemaId);

    BaseRespVo getFieIdInfo(String cinemaId, String fieldId);
}
