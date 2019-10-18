package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.PromoStock;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Zeng-jz
 * @since 2019-10-18
 */
public interface PromoStockMapper extends BaseMapper<PromoStock> {

    Integer selectStockByPromoId(@Param("promoId") Integer uuid);
}
