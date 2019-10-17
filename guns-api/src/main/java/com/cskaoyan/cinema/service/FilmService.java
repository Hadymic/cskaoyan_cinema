package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.film.*;

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

    ConditionListVo selectConditionList(ConditionNoVO conditionNoVO);

    FilmsRespVO selectFilms(ConditionNoVO conditionNoVO, Integer showType, Integer nowPage, Integer sortId, Integer pageSize, Integer offset);


    FilmOrderVo selectFilmByFilmId(Integer filmId);

    /**
     * 通过id查filmName
     * @param filmId
     * @return
     */
    String selectNameById(Integer filmId);
}
