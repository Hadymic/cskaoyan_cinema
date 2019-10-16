package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;

public interface OrderService {
    BaseRespVo getPayInfo(String orderId);
}
