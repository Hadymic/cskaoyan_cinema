package com.cskaoyan.cinema.rest.common.persistence.cinema;

import com.cskaoyan.cinema.cinema.CinemaService;


import com.cskaoyan.cinema.vo.cinema.CinemaQueryVo;
import com.cskaoyan.cinema.vo.cinema.CinemaRespVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CinemaController {
    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;

    @RequestMapping("cinema/getCinemas")
    public CinemaRespVo query(CinemaQueryVo cinemaQueryVo) {
        List<CinemaVo> cinemaVo = cinemaService.queryList(cinemaQueryVo);
        CinemaRespVo cinemaRespVo = new CinemaRespVo();
        cinemaRespVo.setData(cinemaVo);
        return cinemaRespVo;

    }
}
