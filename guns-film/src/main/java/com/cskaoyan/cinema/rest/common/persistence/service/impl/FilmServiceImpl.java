package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.rest.common.exception.FilmExceptionEnum;
import com.cskaoyan.cinema.rest.common.persistence.dao.*;
import com.cskaoyan.cinema.rest.common.persistence.model.*;
import com.cskaoyan.cinema.rest.common.persistence.vo.*;
import com.cskaoyan.cinema.vo.film.FilmActor;
import com.cskaoyan.cinema.vo.film.FilmInfo;
import com.cskaoyan.cinema.vo.film.FilmInfoVO;
import com.cskaoyan.cinema.vo.film.Films;
import com.cskaoyan.cinema.rest.common.persistence.vo.ImgVO;
import com.cskaoyan.cinema.vo.film.IndexVO;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.film.*;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.*;

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
    public IndexVO selectFilms4Index() {
        IndexVO indexVO = new IndexVO();
        indexVO.setBanners(selectBanners());
        indexVO.setHotFilms(selectFilms(1));
        // 即将上映
        indexVO.setSoonFilms(selectFilms(2));
        // 今日票房
        indexVO.setBoxRanking(filmTMapper.selectFilmsByStatus(getPage(1), 1));
        // 最受期待
        indexVO.setExpectRanking(filmTMapper.selectFilmsByStatus(getPage(2), 2));
        indexVO.setTop100(filmTMapper.selectFilmsByStatus(getPage(3), 3));
        return indexVO;
    }

    @Override
    public FilmInfoVO selectFilmInfo(String name, Integer searchType) {
        FilmInfoVO filmInfoVO = new FilmInfoVO();
        FilmT film = getFilmBySearchType(name, searchType);
        if (film == null) {
            return null;
        }
        FilmInfoT filmInfo = new FilmInfoT();
        filmInfo.setFilmId(String.valueOf(film.getUuid()));
        filmInfo = filmInfoTMapper.selectOne(filmInfo);

        filmInfoVO.setFilmName(film.getFilmName());
        filmInfoVO.setFilmEnName(filmInfo.getFilmEnName());
        filmInfoVO.setImgAddress(film.getImgAddress());
        filmInfoVO.setScore(filmInfo.getFilmScore());
        filmInfoVO.setScoreNum(filmInfo.getFilmScoreNum() + "万人评分");
        filmInfoVO.setTotalBox(getBox(film.getFilmBoxOffice()));

        List list = convertString2List(film.getFilmCats());
        EntityWrapper<CatDictT> wrapper = new EntityWrapper<>();
        wrapper.in("UUID", list);
        List<CatDictT> catDictList = catDictTMapper.selectList(wrapper);
        filmInfoVO.setInfo01(convertList2String(catDictList));

        filmInfoVO.setInfo02(sourceDictTMapper.selectById(film.getFilmSource()).getShowName() + "/" + filmInfo.getFilmLength() + "分钟");

        filmInfoVO.setInfo03(convertDate2String(film.getFilmTime()) + "大陆上映");

        FilmActors filmActors = new FilmActors();
        Actors actors = new Actors();
        filmActors.setBiography(filmInfo.getBiography());
        ActorT actor = actorTMapper.selectById(filmInfo.getDirectorId());
        actors.setDirector(new FilmActor(actor.getActorImg(), actor.getActorName()));
        List<FilmActor> actorList = filmActorTMapper.selectFilmActors(film.getUuid());
        actors.setActors(actorList);
        filmActors.setActors(actors);
        filmActors.setFilmId(film.getUuid());
        filmActors.setImgVO(getSub2Img(filmInfo.getFilmImgs()));
        filmInfoVO.setInfo04(filmActors);
        filmInfoVO.setFilmId(film.getUuid());
        return filmInfoVO;
    }

    /**
     * 查询轮播图
     *
     * @return
     */

    private List<Banner> selectBanners() {
        EntityWrapper<BannerT> wrapper = new EntityWrapper<>();
        wrapper.eq("is_valid", 0);
        List<BannerT> bannerTs = bannerTMapper.selectList(wrapper);
        List<Banner> banners = new ArrayList<>();
        for (BannerT bannert : bannerTs) {
            Banner banner = new Banner(bannert.getUuid(), bannert.getBannerAddress(), bannert.getBannerUrl());
            banners.add(banner);
        }
        return banners;
    }


    /**
     * 查询返回类型有num的电影
     *
     * @param status
     * @return
     */

    private Films selectFilms(Integer status) {
        Films films = new Films();
        Page<FilmInfo> page = getPage(status);
        films.setFilmInfo(filmTMapper.selectFilmsByStatus(page, status));
        films.setFilmNum((int) page.getTotal());
        return films;
    }


    /**
     * 获得分页条件
     *
     * @param status
     * @return
     */

    private Page<FilmInfo> getPage(Integer status) {
        Page<FilmInfo> page = new Page<>(1, 10);
        if (status == 1) {
            page.setDescs(Collections.singletonList("film_box_office"));
        } else if (status == 2) {
            page.setDescs(Collections.singletonList("film_preSaleNum"));
        } else {
            page.setDescs(Collections.singletonList("film_score"));
        }
        return page;
    }


    /**
     * 根据查询类型得到电影
     *
     * @param name
     * @param searchType
     * @return
     */

    private FilmT getFilmBySearchType(String name, Integer searchType) {
        if (searchType == 0) {
            Integer id = Integer.valueOf(name);
            return filmTMapper.selectById(id);
        } else {
            FilmT film = new FilmT();
            film.setFilmName(name);
            return filmTMapper.selectOne(film);
        }
    }


    /**
     * 获得电影票房
     *
     * @param totalBox
     * @return
     */

    private String getBox(Integer totalBox) {
        if (totalBox < 10000) {
            return totalBox + "万";
        } else {
            double newNum = totalBox / 10000.0;
            return String.format("%." + 2 + "f", newNum) + "亿";
        }
    }


    /**
     * 转换String
     *
     * @return
     */

    private List convertString2List(String filmCats) {
        return Arrays.asList(filmCats.substring(1, filmCats.length() - 1).split("#"));
    }


    /**
     * 转换List
     *
     * @return
     */

    private String convertList2String(List<CatDictT> catDictList) {
        StringBuilder s = new StringBuilder();
        for (CatDictT catDict : catDictList) {
            s.append(",").append(catDict.getShowName());
        }
        return s.substring(1);
    }


    /**
     * 转换日期
     *
     * @param filmTime
     * @return
     */

    private String convertDate2String(Date filmTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(filmTime);
    }


    /**
     * 给imgVO赋值
     *
     * @param str
     * @return
     */

    private ImgVO getSub2Img(String str) {
        ImgVO imgVO = new ImgVO();
        String[] sub = str.split(",");
        imgVO.setMainImg(sub[0]);
        imgVO.setImg01(sub[1]);
        imgVO.setImg02(sub[2]);
        imgVO.setImg03(sub[3]);
        imgVO.setImg04(sub[4]);
        return imgVO;
    }

    @Override
    public ConditionListVo selectConditionList(ConditionNoVO conditionNoVO) {
        List<CatInfoVo> catInfo = catDictTMapper.selectAll();
        List<SourceInfoVo> sourceInfo = sourceDictTMapper.selectAll();
        List<YearInfoVo> yearInfo = yearDictTMapper.selectAll();

        for (int i = 0; i < catInfo.size(); i++) {
            CatInfoVo catInfoVo = catInfo.get(i);
            if (catInfoVo.getCatId() == conditionNoVO.getCatId()) {
                catInfoVo.setActive(true);
                catInfo.set(i, catInfoVo);
            }
        }
        for (int i = 0; i < sourceInfo.size(); i++) {
            SourceInfoVo sourceInfoVo = sourceInfo.get(i);
            if (sourceInfoVo.getSourceId() == conditionNoVO.getSourceId()) {
                sourceInfoVo.setActive(true);
                sourceInfo.set(i, sourceInfoVo);
            }
        }
        for (int i = 0; i < yearInfo.size(); i++) {
            YearInfoVo yearInfoVo = yearInfo.get(i);
            if (yearInfoVo.getYearId() == conditionNoVO.getYearId()) {
                yearInfoVo.setActive(true);
                yearInfo.set(i, yearInfoVo);
            }
        }

        return new ConditionListVo(catInfo, sourceInfo, yearInfo);
    }

    @Override
    public FilmsRespVO selectFilms(ConditionNoVO conditionNoVO, Integer showType, Integer nowPage,
                                   Integer sortId, Integer pageSize, Integer offset) {
        FilmsRespVO<List> filmsVo = new FilmsRespVO<>();

        String film_catId = "#" + conditionNoVO.getCatId() + "#";
        List<FilmT> films = filmTMapper.selectByIds(conditionNoVO.getSourceId(),
                conditionNoVO.getYearId(), film_catId, showType);
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
            default:
                throw new GunsException(FilmExceptionEnum.VAR_REQUEST_NULL);
        }
        Page<FilmT> filmTPage = new Page<>();
        filmTPage.setSize(pageSize);
        filmTPage.setCurrent(offset + 1);
        filmTPage.setOrderByField(orderByField);

        EntityWrapper<FilmT> entityWrapper = new EntityWrapper<>();
        entityWrapper.like( conditionNoVO.getCatId() != 99, "film_cats", film_catId);
        entityWrapper.eq(conditionNoVO.getSourceId() != 99, "film_source", conditionNoVO.getSourceId());
        entityWrapper.eq(conditionNoVO.getYearId() != 99, "film_date", conditionNoVO.getYearId());

        List<FilmT> filmTS = filmTMapper.selectPage(filmTPage, entityWrapper);

        List<FilmsTVo> filmsTVos = new ArrayList<>();

        for (FilmT filmT : filmTS) {
            FilmsTVo filmsTVo = new FilmsTVo();
            filmsTVo.setBoxNum(filmT.getFilmBoxOffice());
            filmsTVo.setExpectNum(filmT.getFilmPresalenum());
            filmsTVo.setFilmId(filmT.getUuid());
            filmsTVo.setFilmScore(filmT.getFilmScore());
            filmsTVo.setScore(filmT.getFilmScore());
            filmsTVo.setImgAddress(filmT.getImgAddress());
            filmsTVo.setShowTime(filmT.getFilmTime());
            filmsTVo.setFilmType(filmT.getFilmType());
            filmsTVos.add(filmsTVo);
        }

        filmsVo.setData(filmsTVos);
        filmsVo.setNowPage(offset+1);
        filmsVo.setTotalPage(filmTPage.getTotal());
        filmsVo.setImgPre("http://img.meetingshop.cn/");

        return filmsVo;
    }


    @Override
    public FilmOrderVo selectFilmByFilmId(Integer filmId) {
        FilmOrderVo filmOrderVo = filmTMapper.selectFilmByFilmId(filmId);
        return filmOrderVo;
    }

    /**
     * 根据id查name
     *
     * @param filmId
     * @return
     */

    @Override
    public String selectNameById(Integer filmId) {
        FilmT filmT = filmTMapper.selectById(filmId);
        return filmT.getFilmName();
    }
}

