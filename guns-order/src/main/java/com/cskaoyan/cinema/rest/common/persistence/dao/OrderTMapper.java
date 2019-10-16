package com.cskaoyan.cinema.rest.common.persistence.dao;

import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.baomidou.mybatisplus.mapper.BaseMapper;

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

    /**
     * 查询售出的座位编号列表
     * @param fieldId
     * @return
     */
    List<String> selectSoldSeats(Integer fieldId);
}
