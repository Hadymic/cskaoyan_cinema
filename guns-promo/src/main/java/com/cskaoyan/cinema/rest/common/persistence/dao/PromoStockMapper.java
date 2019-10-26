package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.PromoStock;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author hadymic
 * @since 2019-10-18
 */
public interface PromoStockMapper extends BaseMapper<PromoStock> {

    Integer decreaseStock(@Param("promoId") Integer promoId, @Param("amount") Integer amount);
}
