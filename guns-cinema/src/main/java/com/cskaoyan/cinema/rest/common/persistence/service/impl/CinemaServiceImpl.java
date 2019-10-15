package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.CinemaTMapper;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import org.apache.dubbo.config.annotation.Service;
import com.cskaoyan.cinema.rest.common.persistence.dao.AreaDictTMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.CatDictTMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.HallDictTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.CatDictT;
import com.cskaoyan.cinema.vo.AreaVo;
import com.cskaoyan.cinema.vo.BrandVo;
import com.cskaoyan.cinema.vo.ConditionVo;
import com.cskaoyan.cinema.vo.HalltypeVo;
import com.google.common.base.Equivalence;
import com.sun.org.apache.xml.internal.resolver.CatalogManager;
import org.apache.dubbo.config.annotation.Service;
import org.apache.zookeeper.data.Id;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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
        List<CinemaVo> cinemaVo = cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(), cinemaQueryVo.getAreaId());

        return cinemaVo;
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
