package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;

public interface OrderService {
    BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName,Integer userId);
}
