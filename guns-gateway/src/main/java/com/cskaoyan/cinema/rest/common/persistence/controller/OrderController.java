package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {
    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;
}
