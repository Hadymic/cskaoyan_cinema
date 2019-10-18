package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.Promo;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zeng-jz
 * @since 2019-10-18
 */
public interface PromoMapper extends BaseMapper<Promo> {

    List<GetPromoVo> selectByCinemaId(@Param("cinemaId") Integer cinemaId);
}
