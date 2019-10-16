package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.*;

public interface CinemaService {

    ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId);
    ListBean<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    CinemaMsgVo queryCinemaMsg(String cinemaId);

    FieldInfoVo getFieIdInfo(String cinemaId, String fieldId);
}
