package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class OrderController {
    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;
    @Autowired
    private JedisUtils jedisUtils;

    @PostMapping("order/buyTickets")
    public BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName, HttpServletRequest request) {
        Integer userId = jedisUtils.getUserId(request);
        BaseRespVo baseRespVo = orderService.buyTickets(fieldId, soldSeats, seatsName,userId);
        return baseRespVo;
    }
}
