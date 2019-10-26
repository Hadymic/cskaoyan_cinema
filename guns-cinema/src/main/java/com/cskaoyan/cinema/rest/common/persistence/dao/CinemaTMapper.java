package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.CinemaT;
import com.cskaoyan.cinema.vo.cinema.CinemaInfoVo;
import com.cskaoyan.cinema.vo.cinema.CinemaVo;
import com.cskaoyan.cinema.vo.cinema.FilmFields;
import com.cskaoyan.cinema.vo.cinema.FilmMsgVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影院信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-14
 */
public interface CinemaTMapper extends BaseMapper<CinemaT> {

    List<CinemaVo> queryCinemaMsg(Integer brandId, Integer areaId);

    List<FilmMsgVo> queryFilmMsg(Integer cinemaId);

    CinemaInfoVo selectCinemaMsg(@Param("cinemaId") Integer cinemaId);

    List<FilmFields> queryHall(@Param("cinemaId") Integer cinemaId, @Param("filmId") Integer filmId);

    String queryHallById(Integer brandId, Integer areaId);

    List<CinemaVo> queryAllCinema();
}
