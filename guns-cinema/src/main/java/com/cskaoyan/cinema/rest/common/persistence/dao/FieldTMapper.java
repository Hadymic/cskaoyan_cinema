package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.FieldT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.cinema.FieldInfoVo;
import com.cskaoyan.cinema.vo.cinema.FilmInfoVo;
import com.cskaoyan.cinema.vo.cinema.HallInfoVo;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 放映场次表 Mapper 接口
 * </p>
 *
 * @author stylefeng
 * @since 2019-10-14
 */
public interface FieldTMapper extends BaseMapper<FieldT> {

    HallInfoVo selectHallInfo(@Param("cinemaId") String cinemaId,
                              @Param("fieldId") String fieldId);

    FilmInfoVo selectOneById(@Param("fieldId") String fieldId);
}
