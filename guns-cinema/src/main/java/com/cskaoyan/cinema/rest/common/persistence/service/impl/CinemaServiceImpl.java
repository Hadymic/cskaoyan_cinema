package com.cskaoyan.cinema.rest.common.persistence.service.impl;

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


    public ListBean queryList(CinemaQueryVo cinemaQueryVo) {
        PageHelper.startPage(cinemaQueryVo.getNowPage(), cinemaQueryVo.getPageSize());
        boolean flag = (cinemaQueryVo.getBrandId() == 99 || cinemaQueryVo.getAreaId() == 99);
        List<CinemaVo> cinemaVos;
        if (flag == true) {
            cinemaVos = cinemaTMapper.queryAllCinema();

        } else {
            cinemaVos = cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(), cinemaQueryVo.getAreaId());
        }

        List<CinemaVo> cinemaList = new ArrayList<>();

        //判断影院是否存在相应的影厅
        for (CinemaVo cinemaVo : cinemaVos) {
            //传入的影厅
            Integer halltypeId = cinemaQueryVo.getHalltypeId();

            String halls = cinemaTMapper.queryHallById(cinemaQueryVo.getBrandId(), cinemaQueryVo.getAreaId());
            if (halltypeId == 99) {
                cinemaList.add(cinemaVo);

            } else if (halls.contains(halltypeId.toString())) {
                cinemaList.add(cinemaVo);

            }
        }

        PageHelper.startPage(cinemaQueryVo.getNowPage(), cinemaQueryVo.getPageSize());
        List<CinemaVo> cinemaVo = cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(), cinemaQueryVo.getAreaId());
        PageInfo<CinemaVo> cinemaPageInfo = new PageInfo<>(cinemaVo);

        //总记录
        long total = cinemaPageInfo.getTotal();
        //总页数
        long totalPage = total / cinemaQueryVo.getPageSize();

        ListBean cinemaLists = new ListBean<>();
        cinemaLists.setData(cinemaList);
        cinemaLists.setNowPage(cinemaQueryVo.getNowPage());
        cinemaLists.setTotalPage(totalPage + 1);
        return cinemaLists;
    }

    @Override
    public CinemaMsgVo queryCinemaMsg(String cinemaId) {
        CinemaMsgVo cinemaMsgVo = new CinemaMsgVo();
        HashMap map = new HashMap();
        CinemaInfoVo cinemaMsg = cinemaTMapper.selectCinemaMsg(cinemaId);
        cinemaMsg.setImgUrl(cinemaMsg.getImgUrl());
        List<FilmMsgVo> filmList = cinemaTMapper.queryFilmMsg(cinemaId);
        for (FilmMsgVo filmMsgVo : filmList) {
            String filmType = filmMsgVo.getFilmType();
            Integer filmId = filmMsgVo.getFilmId();
            List<FilmFields> filmFields = cinemaTMapper.queryHall(filmId);
            for (FilmFields filmField : filmFields) {
                filmField.setLanguage(filmType);
            }
            filmMsgVo.setFilmFields(filmFields);
            filmMsgVo.setFilmType(filmType);
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
    public FieldInfoVo getFieIdInfo(String cinemaId, String fieldId) {
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
        return cinemaTMapper.selectCinemaMsg("" + cinemaId);
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
