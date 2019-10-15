package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.film.FilmVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.film.ConditionNoVO;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmService.class)
    private FilmService filmService;

    @RequestMapping("getIndex")
    public FilmVO getIndex(){
        Object indexVO = filmService.selectFilms4Index();
        if (indexVO == null) {
            return new FilmVO(1,"查询失败，无影片可加载");
        }
        return new FilmVO<>(0,indexVO,null);
    }

    @RequestMapping("films/{name}")
    public FilmVO getFilmInfo(@PathVariable String name, Integer searchType) {
        if (searchType != 0 && searchType != 1) {
            return new FilmVO(400, "系统出现异常，输入不合法");
        }
        Object filmInfoVO = filmService.selectFilmInfo(name, searchType);
        if (filmInfoVO == null) {
            return new FilmVO(1, "查询失败，无影片可加载");
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
        Object conditionVo = filmService.selectConfitionList(conditionNoVO);
        if (conditionVo != null) {
            return new BaseRespVo<>(0, conditionVo, null);
        } else {
            return new BaseRespVo<>(1, null, "查询失败，无条件可加载");
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
    public Object getFilms(@RequestBody ConditionNoVO conditionNoVO, int showType,
                           int sortId, int pageSize, int offset) {
        Object films = filmService.selectFilms(conditionNoVO, showType, sortId, pageSize, offset);
        return films;

    }
}
