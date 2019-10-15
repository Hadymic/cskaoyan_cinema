package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.film.ConditionNoVO;

public interface FilmService {
    Object selectConfitionList(ConditionNoVO conditionNoVO);

    Object selectFilms(ConditionNoVO conditionNoVO, int showType, int sortId, int pageSize, int offset);
}
