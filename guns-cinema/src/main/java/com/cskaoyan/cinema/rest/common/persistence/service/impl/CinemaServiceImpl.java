package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.CinemaTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.CinemaT;
import com.cskaoyan.cinema.vo.CinemaQueryVo;
import com.cskaoyan.cinema.vo.CinemaRespVo;
import com.cskaoyan.cinema.vo.CinemaVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaTMapper cinemaTMapper;
    @Override
    public  List<CinemaVo>queryList(CinemaQueryVo cinemaQueryVo) {
//        Page<CinemaT>  page = new Page<>();
//        page.setSize(cinemaQueryVo.getPageSize());
//        page.setCurrent(cinemaQueryVo.getNowPage());
//
//        EntityWrapper<CinemaT> cinemaList = new EntityWrapper<>();
//        List<CinemaVo> cinemaVoList=convert(cinemaList);
        List<CinemaVo> cinemaVo= cinemaTMapper.queryCinemaMsg(cinemaQueryVo.getBrandId(),cinemaQueryVo.getAreaId());

        return cinemaVo;
    }
}
