package com.cskaoyan.cinema.cinema;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.*;
import com.cskaoyan.cinema.vo.cinema.CinemaMsgVo;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import com.cskaoyan.cinema.vo.cinema.ListBean;

import java.util.Date;


public interface CinemaService {

    ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId);
    ListBean<CinemaVo> queryList(CinemaQueryVo cinemaQueryVo);

    CinemaMsgVo queryCinemaMsg(String cinemaId);

    FieldInfoVo getFieIdInfo(String cinemaId, String fieldId);

    /**
     * 根据field的id返回放映时间
     * @param fieldId
     * @return
     */
    Date selectFieldTimeById(Integer fieldId);

    /**
     * 根据cinema的id返回name
     * @param cinemaId
     * @return
     */
    String selectNameById(Integer cinemaId);
}
