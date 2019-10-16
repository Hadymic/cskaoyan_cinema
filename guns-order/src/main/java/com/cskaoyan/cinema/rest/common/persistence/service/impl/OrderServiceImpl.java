package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
}
