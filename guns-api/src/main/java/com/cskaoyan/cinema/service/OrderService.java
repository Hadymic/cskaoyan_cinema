package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.order.OrderVo;

import java.io.IOException;
import java.util.List;

public interface OrderService {
    BaseRespVo getPayInfo(String orderId);


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


    boolean isTrueSeats(Integer fieldId, String soldSeats) throws IOException;

    boolean isNotSoldSeats(Integer fieldId, String soldSeats);

    /**
     * 获取用户订单信息接口
     * @param nowPage
     * @param pageSize
     * @param userId
     * @return
     */
    List<OrderVo> getOrderInfo(Integer nowPage, Integer pageSize, Integer userId);

}
