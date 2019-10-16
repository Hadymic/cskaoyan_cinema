package com.cskaoyan.cinema.service;

public interface OrderService {

    /**
     * 根据FieldId获取所有已经销售的座位编号
     * @param fieldId
     * @return
     */
    String getSoldSeatsByFieldId(Integer fieldId);

    /**
     * 获取用户订单信息接口
     * @param nowPage
     * @param pageSize
     * @param userId
     * @return
     */
    Object getOrderInfo(Integer nowPage, Integer pageSize, int userId);
}
