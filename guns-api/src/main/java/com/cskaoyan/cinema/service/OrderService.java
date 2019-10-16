package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;

public interface OrderService {
    BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName,Integer userId);
    /**
     * 获取支付结果
     *
     * @param orderId
     * @return
     */
    boolean getPayResult(String orderId);

    /**
     * 根据FieldId获取所有已经销售的座位编号
     *
     * @param fieldId
     * @return
     */
    String getSoldSeatsByFieldId(Integer fieldId);
}
