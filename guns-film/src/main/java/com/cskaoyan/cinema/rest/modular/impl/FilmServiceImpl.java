package com.cskaoyan.cinema.rest.modular.impl;

import com.cskaoyan.cinema.rest.common.persistence.dao.*;
import com.cskaoyan.cinema.rest.common.persistence.vo.FilmInfoVO;
import com.cskaoyan.cinema.rest.common.persistence.vo.IndexVO;
import com.cskaoyan.cinema.service.FilmService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = FilmService.class)
public class FilmServiceImpl implements FilmService {
    @Autowired
    private ActorTMapper actorTMapper;
    @Autowired
    private BannerTMapper bannerTMapper;
    @Autowired
    private CatDictTMapper catDictTMapper;
    @Autowired
    private FilmActorTMapper filmActorTMapper;
    @Autowired
    private FilmInfoTMapper filmInfoTMapper;
    @Autowired
    private FilmTMapper filmTMapper;
    @Autowired
    private SourceDictTMapper sourceDictTMapper;
    @Autowired
    private YearDictTMapper yearDictTMapper;


    @Override
    public Object selectFilms4Index() {
        IndexVO indexVO = new IndexVO();
        indexVO.setBanners(selectBanners());
        indexVO.setHotFilms(selectHotFilms());
        indexVO.setSoonFilms(selectSoonFilms());
        indexVO.setBoxRanking(selectBoxRanking);
        indexVO.setExpectRankings(selectExpectRankings());
        indexVO.setTop100(selectTop());
        return indexVO;
    }

    @Override
    public Object selectFilmInfo(String name, Integer searchType) {
        return new FilmInfoV;
    }
}
