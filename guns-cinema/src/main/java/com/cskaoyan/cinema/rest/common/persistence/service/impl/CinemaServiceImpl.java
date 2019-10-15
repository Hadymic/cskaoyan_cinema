package com.cskaoyan.cinema.rest.common.persistence.service.impl;

<<<<<<< HEAD
import com.baomidou.mybatisplus.plugins.pagination.PageHelper;
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.CinemaTMapper;
import com.cskaoyan.cinema.vo.cinema.*;
import com.github.pagehelper.PageInfo;
=======
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.CinemaTMapper;
import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
>>>>>>> 82aecced46fd54d4b0c35bdbbdc7e7d982ede52d
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = CinemaService.class)
public class CinemaServiceImpl implements CinemaService {
    @Autowired
    CinemaTMapper cinemaTMapper;
    @Override
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
}
