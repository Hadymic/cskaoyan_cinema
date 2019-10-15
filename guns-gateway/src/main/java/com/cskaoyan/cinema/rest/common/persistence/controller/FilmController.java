package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.film.ConditionNoVO;
import com.cskaoyan.cinema.vo.film.ConditionVO;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("film")
public class FilmController {
    @Reference(interfaceClass = FilmService.class)
    private FilmService filmService;

    /**
     * Zeng-jz
     * @param conditionNoVO
     * @return
     */
    @RequestMapping("getConditionList")
    public BaseRespVo getConditionList(ConditionNoVO conditionNoVO){
        Object conditionVo = filmService.selectConfitionList(conditionNoVO);
        if (conditionVo != null) {
            BaseRespVo<Object> condition = new BaseRespVo<>();
            condition.setStatus(0);
            condition.setData(conditionVo);
            condition.setMsg(null);
            return condition;
        }else {
            BaseRespVo<Object> condition = new BaseRespVo<>();
            condition.setStatus(1);
            condition.setData(null);
            condition.setMsg("查询失败，无条件可加载");
            return condition;
        }
    }

    /**
     * Zeng-jz
     * @param conditionNoVO
     * @param showType
     * @param sortId
     * @param pageSize
     * @param offset
     * @return
     */
    @RequestMapping("getFilms")
    public Object getFilms(@RequestBody ConditionNoVO conditionNoVO, int showType,
                                int sortId, int pageSize, int offset){
        Object films = filmService.selectFilms(conditionNoVO, showType, sortId, pageSize, offset);
        return films;
    }
}
