package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.BaseRespVo;

public interface OrderService {
    BaseRespVo buyTickets(Integer fieldId, Integer soldSeats, String seatsName);
}
