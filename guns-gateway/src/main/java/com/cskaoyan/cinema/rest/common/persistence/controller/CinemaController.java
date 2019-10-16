package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;

    /**
     * author:zt
     * 1、查询影院列表-根据条件查询所有影院
     * @param cinemaQueryVo
     * @return
     */
    @RequestMapping("cinema/getCinemas")
    public     ListBean query(CinemaQueryVo cinemaQueryVo) {
        ListBean cinemaList = cinemaService.queryList(cinemaQueryVo);
        return  cinemaList;

    }

    /**2获得影院列表查询
     * @param brandId
     * @param hallType
     * @param areaId
     * @return
     */
    @RequestMapping("cinema/getCondition")
    public BaseRespVo selectGetCondition(Integer brandId, Integer hallType, Integer areaId){
        ConditionVo conditionVo = cinemaService.selectCondition(brandId, hallType, areaId);
        BaseRespVo baseRespVo = new BaseRespVo(0,conditionVo,null);
        return baseRespVo;
    }
    /**
     * author:zt
     *
     * 3、获取播放场次接口
     */
    @RequestMapping("cinema/getFields")
    public CinemaMsgVo queryCinemaMsg(String cinemaId){
          CinemaMsgVo cinemaMsgVo=cinemaService.queryCinemaMsg(cinemaId);
          return  cinemaMsgVo;
    }

    /**
     * 获取场次详细详细接口
     * @return
     */
    @RequestMapping(value = "cinema/getFieldInfo",method = RequestMethod.POST)
    public BaseRespVo getFieIdInfo(@RequestBody String cinemaId, String fieldId){
        BaseRespVo fieIdInfo = cinemaService.getFieIdInfo(cinemaId, fieldId);
        return fieIdInfo;
    }

}
