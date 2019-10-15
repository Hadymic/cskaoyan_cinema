package com.cskaoyan.cinema.service;

public interface FilmService {
    /**
     * 查询首页的电影
     * @return
     */
    Object selectFilms4Index();


    /**
     * 查询电影详细信息
     * @param name
     * @param searchType
     * @return
     */
    Object selectFilmInfo(String name, Integer searchType);
}
