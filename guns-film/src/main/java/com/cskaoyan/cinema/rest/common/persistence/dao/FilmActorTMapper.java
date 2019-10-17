package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.FilmActorT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.film.FilmActor;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影片与演员映射表 Mapper 接口
 * </p>
 *
 * @author jszza
 * @since 2019-10-14
 */
public interface FilmActorTMapper extends BaseMapper<FilmActorT> {

    /**
     * 查询电影饰演成员
     * @param filmId
     * @return
     */
    List<FilmActor> selectFilmActors(@Param("filmId") Integer filmId);
}
