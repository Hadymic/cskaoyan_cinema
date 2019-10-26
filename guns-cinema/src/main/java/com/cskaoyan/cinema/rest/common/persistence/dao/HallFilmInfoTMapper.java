package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.HallFilmInfoT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.cinema.FilmInfoVo;
import com.cskaoyan.cinema.vo.cinema.HalltypeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 影厅电影信息表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-14
 */
public interface HallFilmInfoTMapper extends BaseMapper<HallFilmInfoT> {
    FilmInfoVo selectByfieldId(@Param("cinemaId") Integer cinemaId,
                               @Param("fieldId") Integer fieldId);

}
