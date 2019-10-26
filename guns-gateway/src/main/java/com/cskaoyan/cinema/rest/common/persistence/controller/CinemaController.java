package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.CinemaMsgVo;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.FieldInfoVo;
import com.cskaoyan.cinema.vo.cinema.ListBean;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("cinema")
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

    /**
     * author:zt
     * 1、查询影院列表-根据条件查询所有影院
     *
     * @param cinemaQueryVo
     * @return
     */
    @RequestMapping("getCinemas")
    public ListBean query(CinemaQueryVo cinemaQueryVo) {
        ListBean cinemaList = cinemaService.queryList(cinemaQueryVo);
        cinemaList.setImgPre(null);
        cinemaList.setStatus(0);
        cinemaList.setMsg(null);
        return cinemaList;

    }

    /**
     * 2获得影院列表查询
     *
     * @param brandId
     * @param hallType
     * @param areaId
     * @return
     */
    @RequestMapping("getCondition")
    public BaseRespVo selectGetCondition(Integer brandId, Integer hallType, Integer areaId) {
        ConditionVo conditionVo = cinemaService.selectCondition(brandId, hallType, areaId);
        return new BaseRespVo<>(0, conditionVo, null);
    }

    /**
     * author:zt
     * <p>
     * 3、获取播放场次接口
     */
    @RequestMapping("getFields")
    public CinemaMsgVo queryCinemaMsg(Integer cinemaId) {
        return cinemaService.queryCinemaMsg(cinemaId);
    }

    /**
     * 获取场次详细详细接口
     *
     * @return
     **/
    @PostMapping("getFieldInfo")
    public BaseRespVo getFieIdInfo(Integer cinemaId, Integer fieldId) {
        FieldInfoVo fieldInfoVo = cinemaService.getFieIdInfo(cinemaId, fieldId);
        BaseRespVo baseRespVo = new BaseRespVo<>(0, fieldInfoVo, "");
        baseRespVo.setImgPre("http://img.meetingshop.cn/");
        return baseRespVo;
    }
}
