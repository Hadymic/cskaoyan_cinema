package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;

/**
 * <p>
 * 订单信息表 Mapper 接口
 * </p>
 *
 * @author hadymic
 * @since 2019-10-16
 */
public interface OrderTMapper extends BaseMapper<OrderT> {

    OrderT queryOrderMsg(Integer fieldId);

    Integer queryFilmPrice(Integer fieldId);

 boolean insertDb(OrderT orderT);

    String queryFilmName(Integer filmId);

    String queryFieldTime(Integer fieldId);

    String queryCinema(Integer fieldId);
}
