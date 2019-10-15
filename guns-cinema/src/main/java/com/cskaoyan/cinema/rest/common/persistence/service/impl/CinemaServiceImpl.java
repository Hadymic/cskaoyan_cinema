package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.AreaDictTMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.CatDictTMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.CinemaTMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.HallDictTMapper;
import com.cskaoyan.cinema.vo.AreaVo;
import com.cskaoyan.cinema.vo.BrandVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.HalltypeVo;
import com.cskaoyan.cinema.vo.cinema.*;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    CinemaTMapper cinemaTMapper;
    @Override
    public  List<CinemaVo>queryList(CinemaQueryVo cinemaQueryVo) {
//        Page<CinemaT>  page = new Page<>();
//        page.setSize(cinemaQueryVo.getPageSize());
//        page.setCurrent(cinemaQueryVo.getNowPage());
//
//        EntityWrapper<CinemaT> cinemaList = new EntityWrapper<>();
//        List<CinemaVo> cinemaVoList=convert(cinemaList);
        return cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(), cinemaQueryVo.getAreaId());
    }
    public  ListBean  queryList(CinemaQueryVo cinemaQueryVo) {
        PageHelper.startPage(cinemaQueryVo.getNowPage(),cinemaQueryVo.getPageSize());
        List<CinemaVo> cinemaVo= cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(),cinemaQueryVo.getAreaId());
        PageInfo<CinemaVo> cinemaPageInfo = new PageInfo<>(cinemaVo);
        //总记录
        long total = cinemaPageInfo.getTotal();
        //总页数
        long totalPage=total/cinemaQueryVo.getPageSize();

        ListBean cinemaList = new ListBean<>();
        cinemaList.setData(cinemaVo);
        cinemaList.setNowPage(cinemaQueryVo.getNowPage());
        cinemaList.setTotalPage(totalPage);
        return cinemaList;
    }

    @Override
    public CinemaMsgVo queryCinemaMsg(String cinemaId) {
        CinemaMsgVo cinemaMsgVo = new CinemaMsgVo();
        List list = new ArrayList();
        CinemaInfoVo cinemaMsg =cinemaTMapper.selectCinemaMsg(cinemaId);
        List<FilmMsgVo> filmMsgVos=cinemaTMapper.queryFilmMsg(cinemaId);
        for (FilmMsgVo filmMsgVo : filmMsgVos) {
            String filmType = filmMsgVo.getFilmType();
            Integer filmId = filmMsgVo.getFilmId();
           List<FilmFields> filmFields=cinemaTMapper.queryHall(filmId);
            for (FilmFields filmField : filmFields) {
                filmField.setLanguage(filmType);
            }
           filmMsgVo.setFilmFields(filmFields);
        }
       list.add(cinemaMsg);
       list.add(filmMsgVos);
       cinemaMsgVo.setData(list);
       cinemaMsgVo.setImgPre(null);
       cinemaMsgVo.setNowPage(null);
       cinemaMsgVo.setStatus(0);
       cinemaMsgVo.setTotalPage(null);
        return cinemaMsgVo;
    }
    @Autowired
    CatDictTMapper catDictTMapper;
    @Autowired
    AreaDictTMapper areaDictTMapper;
    @Autowired
    HallDictTMapper hallDictTMapper;
    public ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId) {
        ConditionVo conditionVo = new ConditionVo();
        List<BrandVo> brandVos = catDictTMapper.selectListByUUID(brandId);
        for (BrandVo brandVo : brandVos) {
            if (brandVo.getBrandId() == brandId) brandVo.setActive(true);
        }
        conditionVo.setBrandList(brandVos);
        List<AreaVo> areaVos  = areaDictTMapper.selectListByUUID(areaId);
        for (AreaVo areaVo : areaVos) {
            if (areaVo.getAreaId() == areaId ) areaVo.setActive(true);
        }
        conditionVo.setAreaList(areaVos);
        List<HalltypeVo> halltypeVos  = hallDictTMapper.selectListByUUID(hallType);
        for (HalltypeVo halltypeVo : halltypeVos) {
            if (halltypeVo.getHallType() == hallType ) halltypeVo.setActive(true);
        }
        conditionVo.setHalltypeList(halltypeVos);
        return conditionVo;
    }
}
