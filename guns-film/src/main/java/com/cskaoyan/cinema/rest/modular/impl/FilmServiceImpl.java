package com.cskaoyan.cinema.rest.modular.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.rest.common.persistence.dao.*;
import com.cskaoyan.cinema.rest.common.persistence.model.FilmT;
import com.cskaoyan.cinema.rest.common.persistence.vo.*;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.film.ConditionNoVO;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

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
    public Object selectConfitionList(ConditionNoVO conditionNoVO) {
        List<CatInfoVo> catInfo = catDictTMapper.selectAll();
        List<SourceInfoVo> sourceInfo = sourceDictTMapper.selectAll();
        List<YearInfoVo> yearInfo = yearDictTMapper.selectAll();

        for(int i = 0; i < catInfo.size(); i++) {
            CatInfoVo catInfoVo = catInfo.get(i);
            if (catInfoVo.getCatId() == conditionNoVO.getCatId()){
                catInfoVo.setActive(true);
                catInfo.set(i, catInfoVo);
            }
        }
        for(int i = 0; i < sourceInfo.size(); i++) {
            SourceInfoVo sourceInfoVo = sourceInfo.get(i);
            if (sourceInfoVo.getSourceId() == conditionNoVO.getSourceId()){
                sourceInfoVo.setActive(true);
                sourceInfo.set(i, sourceInfoVo);
            }
        }
        for(int i = 0; i < yearInfo.size(); i++) {
            YearInfoVo yearInfoVo = yearInfo.get(i);
            if (yearInfoVo.getYearId() == conditionNoVO.getYearId()){
                yearInfoVo.setActive(true);
                yearInfo.set(i, yearInfoVo);
            }
        }

        ConditionVo conditionVo = new ConditionVo(catInfo, sourceInfo, yearInfo);
        return conditionVo;
    }

    @Override
    public FilmsVo selectFilms(ConditionNoVO conditionNoVO, int showType, int sortId, int pageSize, int offset) {
        FilmsVo filmsVo = new FilmsVo();

        String film_catId = "%#" + conditionNoVO.getCatId() + "#%";
        List<FilmT> films = filmTMapper.selectByIds(conditionNoVO.getSourceId(),
                conditionNoVO.getYearId(),film_catId,showType);
        String orderByField = null;
        switch (sortId) {
            case 1:
                orderByField = "film_status";
                break;
            case 2:
                orderByField = "film_time";
                break;
            case 3:
                orderByField = "film_score";
                break;
        }
        Page<FilmT> filmList = new Page<FilmT>(offset, pageSize, orderByField);
        Page filmTPage = filmList.setRecords(films);

        filmsVo.setData(filmTPage.getRecords());
        filmsVo.setStatus(0);
        filmsVo.setNowPage(filmTPage.getCurrent());
        filmsVo.setTotalPage(filmTPage.getTotal());
        filmsVo.setImgPre("http://img.meetingshop.cn/");

        return filmsVo;
    }

}
