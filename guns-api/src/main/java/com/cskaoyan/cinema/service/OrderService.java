package com.cskaoyan.cinema.service;

public interface OrderService {

    /**
     * 根据FieldId获取所有已经销售的座位编号
     * @param fieldId
     * @return
     */
    String getSoldSeatsByFieldId(Integer fieldId);
}
