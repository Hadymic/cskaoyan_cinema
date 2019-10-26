package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.cskaoyan.cinema.rest.common.persistence.dao.*;
import com.cskaoyan.cinema.rest.common.persistence.model.CinemaT;
import com.cskaoyan.cinema.rest.common.persistence.model.FieldT;
import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.cinema.*;
import com.github.pagehelper.PageInfo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaTMapper cinemaTMapper;
    @Autowired
    private FieldTMapper fieldTMapper;


    @Override
    public ListBean queryList(CinemaQueryVo cinemaQueryVo) {
        Page page = new Page(cinemaQueryVo.getNowPage(), cinemaQueryVo.getPageSize());
        List<CinemaT> cinemas = cinemaTMapper.selectPage(page, new EntityWrapper<CinemaT>()
                .eq(cinemaQueryVo.getBrandId() != 99, "brand_id", cinemaQueryVo.getBrandId())
                .eq(cinemaQueryVo.getAreaId() != 99, "area_id", cinemaQueryVo.getAreaId())
                .like(cinemaQueryVo.getHallType() != 99, "hall_ids", "#" + cinemaQueryVo.getHallType() + "#"));

        List<CinemaVo> data = new ArrayList<>();
        for (CinemaT cinema : cinemas) {
            CinemaVo cinemaVo = new CinemaVo(cinema.getUuid(), cinema.getCinemaName(), cinema.getCinemaAddress(), cinema.getMinimumPrice());
            data.add(cinemaVo);
        }
        ListBean<CinemaVo> listBean = new ListBean<>();
        listBean.setData(data);
        listBean.setImgPre("http://img.meetingshop.cn/");
        listBean.setStatus(0);
        listBean.setNowPage(cinemaQueryVo.getNowPage());
        listBean.setTotalPage(page.getTotal());
        return listBean;
    }

    @Override
    public CinemaMsgVo queryCinemaMsg(Integer cinemaId) {
        CinemaMsgVo cinemaMsgVo = new CinemaMsgVo();
        HashMap map = new HashMap(2);
        CinemaInfoVo cinemaMsg = cinemaTMapper.selectCinemaMsg(cinemaId);
        cinemaMsg.setImgUrl(cinemaMsg.getImgUrl());
        List<FilmMsgVo> filmList = cinemaTMapper.queryFilmMsg(cinemaId);
        for (FilmMsgVo filmMsgVo : filmList) {
            String filmType = filmMsgVo.getFilmType();
            Integer filmId = filmMsgVo.getFilmId();
            List<FilmFields> filmFields = cinemaTMapper.queryHall(cinemaId, filmId);
            for (FilmFields filmField : filmFields) {
                filmField.setLanguage(filmType);
            }
            filmMsgVo.setFilmFields(filmFields);
        }
        map.put("cinemaInfo", cinemaMsg);
        map.put("filmList", filmList);
        cinemaMsgVo.setData(map);
        cinemaMsgVo.setImgPre("http://img.meetingshop.cn/");
        cinemaMsgVo.setNowPage(null);
        cinemaMsgVo.setStatus(0);
        cinemaMsgVo.setTotalPage(null);
        return cinemaMsgVo;
    }


    //@Autowired
    //表mtime_hall_film_info_t,查询电影信息
    @Autowired
    HallFilmInfoTMapper hallFilmInfoTMapper;

    //表`mtime_field_t,场地信息
    //获取场次详细信息
    @Override
    public FieldInfoVo getFieIdInfo(Integer cinemaId, Integer fieldId) {
        FieldInfoVo fieldInfoVo = new FieldInfoVo();
        CinemaInfoVo cinemaInfoVo = cinemaTMapper.selectCinemaMsg(cinemaId);
        fieldInfoVo.setCinemaInfo(cinemaInfoVo);
        fieldInfoVo.setFilmInfo(hallFilmInfoTMapper.selectByfieldId(cinemaId, fieldId));
        fieldInfoVo.setHallInfo(fieldTMapper.selectHallInfo(fieldId));
        return fieldInfoVo;
    }

    @Override
    public Date selectFieldTimeById(Integer fieldId) {
        FieldT fieldT = fieldTMapper.selectById(fieldId);
        String beginTime = fieldT.getBeginTime();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
        try {
            Date parse = sdf.parse(beginTime);
            System.out.println(parse);
            return parse;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String selectNameById(Integer cinemaId) {
        CinemaT cinemaT = cinemaTMapper.selectById(cinemaId);
        return cinemaT.getCinemaName();
    }

    @Override
    public CinemaInfoVo selectCinemaInfoById(Integer cinemaId) {
        return cinemaTMapper.selectCinemaMsg(cinemaId);
    }

    //`mtime_brand_dict_t`
    @Autowired
    BrandDictTMapper brandDictTMapper;
    //    @Autowired
//    CatDictTMapper catDictTMapper;
    @Autowired
    AreaDictTMapper areaDictTMapper;
    @Autowired
    HallDictTMapper hallDictTMapper;

    //影院列表查询条件
    @Override
    public ConditionVo selectCondition(Integer brandId, Integer hallType, Integer areaId) {
        ConditionVo conditionVo = new ConditionVo();
        List<BrandVo> brandVos = brandDictTMapper.selectListByUUID(brandId);
        List<AreaVo> areaVos = areaDictTMapper.selectListByUUID(areaId);
        List<HalltypeVo> halltypeVos = hallDictTMapper.selectListByUUID(hallType);
        for (HalltypeVo halltypeVo : halltypeVos) {
            if (halltypeVo.getHalltypeId() == hallType) halltypeVo.setActive(true);
        }
        for (BrandVo brandVo : brandVos) {
            if (brandVo.getBrandId() == brandId) brandVo.setActive(true);
        }
        for (AreaVo areaVo : areaVos) {
            if (areaVo.getAreaId() == areaId) areaVo.setActive(true);
        }
        conditionVo.setAreaList(areaVos);
        conditionVo.setBrandList(brandVos);
        conditionVo.setHalltypeList(halltypeVos);
        return conditionVo;
    }

    public CinemaTMapper getCinemaTMapper() {
        return cinemaTMapper;
    }

    public CinemaServiceImpl setCinemaTMapper(CinemaTMapper cinemaTMapper) {
        this.cinemaTMapper = cinemaTMapper;
        return this;
    }
}
