package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderTMapper orderTMapper;
}
