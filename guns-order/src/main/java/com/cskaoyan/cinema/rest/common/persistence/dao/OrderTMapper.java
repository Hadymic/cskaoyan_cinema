package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

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

    String queryFilmName(Integer filmId);

    String queryFieldTime(Integer fieldId);

    String queryCinema(Integer fieldId);

    /**
     * 查询售出的座位编号列表
     * @param fieldId
     * @return
     */
    String selectSoldSeats(Integer fieldId);

    /**
     * 查询影厅的座位
     * @param fieldId
     * @return
     */
    String getSeatMsg(Integer fieldId);

}
