package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.film.ConditionNoVO;
import com.cskaoyan.cinema.vo.film.FilmInfoVO;
import com.cskaoyan.cinema.vo.film.FilmOrderVo;
import com.cskaoyan.cinema.vo.film.IndexVO;

public interface FilmService {

    /**
     * 查询首页的电影
     * @return
     */
    IndexVO selectFilms4Index();


    /**
     * 查询电影详细信息
     * @param name
     * @param searchType
     * @return
     */
    FilmInfoVO selectFilmInfo(String name, Integer searchType);

    Object selectConfitionList(ConditionNoVO conditionNoVO);

    Object selectFilms(ConditionNoVO conditionNoVO, int showType, int sortId, int pageSize, int offset);


    FilmOrderVo selectFilmByFilmId(Integer filmId);

    /**
     * 通过id查filmName
     * @param filmId
     * @return
     */
    String selectNameById(Integer filmId);
}
