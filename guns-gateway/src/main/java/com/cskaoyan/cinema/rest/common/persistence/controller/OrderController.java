package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;

    @PostMapping("order/buyTickets")
    public BaseRespVo buyTickets(Integer fieldId,Integer soldSeats,String seatsName){
                   BaseRespVo  baseRespVo = orderService.buyTickets(fieldId,soldSeats,seatsName);
                   return  baseRespVo;
    }
}
