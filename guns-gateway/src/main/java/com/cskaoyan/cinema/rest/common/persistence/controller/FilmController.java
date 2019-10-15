package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.film.FilmVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PathVariable;
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
    public FilmVO getFilmInfo(@PathVariable String name, Integer searchType){
        if (searchType != 0 && searchType != 1) {
            throw new 
        }
        Object filmInfoVO = filmService.selectFilmInfo(name,searchType);
        if (filmInfoVO == null) {
            return new FilmVO(1,"查询失败，无影片可加载");
        }
        return new FilmVO<>(0,filmInfoVO,null);
    }
}
