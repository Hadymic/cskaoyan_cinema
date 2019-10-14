package com.cskaoyan.cinema.rest.modular.impl;

import com.cskaoyan.cinema.rest.common.persistence.dao.*;
import com.cskaoyan.cinema.service.FilmService;
import org.springframework.beans.factory.annotation.Autowired;

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
}
