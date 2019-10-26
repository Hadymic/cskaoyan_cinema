package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.core.exception.CinemaException;
import com.cskaoyan.cinema.rest.common.cache.CacheService;
import com.cskaoyan.cinema.rest.common.exception.FilmExceptionEnum;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.film.*;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmService.class, check = false)
    private FilmService filmService;

    @Autowired
    private CacheService cacheService;

    @RequestMapping("getIndex")
    public FilmVO getIndex() {
        //如果本地缓存中存在，从缓存中取出
        IndexVO cache = cacheService.getCacheInLocal("indexVO", IndexVO.class);
        if (cache != null) {
            return new FilmVO<>(0, cache, null);
        }

        IndexVO indexVO = filmService.selectFilms4Index();
        if (indexVO == null) {
            throw new CinemaException(FilmExceptionEnum.FILM_NOT_FOUND);
        }
        //放入本地缓存
        cacheService.setCacheInLocal("indexVO", indexVO);
        return new FilmVO<>(0, indexVO, null);
    }

    @RequestMapping("films/{name}")
    public FilmVO getFilmInfo(@PathVariable String name, Integer searchType) {
        if (searchType != 0 && searchType != 1) {
            throw new CinemaException(FilmExceptionEnum.VAR_REQUEST_NULL);
        }
        FilmInfoVO filmInfoVO = filmService.selectFilmInfo(name, searchType);
        if (filmInfoVO == null) {
            throw new CinemaException(FilmExceptionEnum.FILM_NOT_FOUND);
        }
        return new FilmVO<>(0, filmInfoVO, null);
    }

    /**
     * Zeng-jz
     *
     * @param conditionNoVO
     * @return
     */
    @RequestMapping("getConditionList")
    public BaseRespVo getConditionList(ConditionNoVO conditionNoVO) {
        ConditionListVo conditionVo = filmService.selectConditionList(conditionNoVO);
        if (conditionVo == null) {
            throw new CinemaException(FilmExceptionEnum.FILM_NOT_FOUND);
        } else {
            return new BaseRespVo<>(0, conditionVo, null);
        }
    }

    /**
     * Zeng-jz
     *
     * @param conditionNoVO
     * @param showType
     * @param sortId
     * @param pageSize
     * @param offset
     * @return
     */
    @RequestMapping("getFilms")
    public FilmsRespVO getFilms(ConditionNoVO conditionNoVO, Integer showType,
                                Integer sortId, Integer nowPage, Integer pageSize, Integer offset, String kw) {
        FilmsRespVO films = null;
        films = filmService.selectFilms(conditionNoVO, showType, nowPage, sortId, pageSize, offset, kw);
        if (films.getData() == null) {
            throw new CinemaException(FilmExceptionEnum.FILM_NOT_FOUND);
        } else {
            films.setStatus(0);
            return films;
        }
    }
}
