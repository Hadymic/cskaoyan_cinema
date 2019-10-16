package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
<<<<<<< HEAD


    @Override
    public BaseRespVo buyTickets(Integer fieldId, Integer soldSeats, String seatsName) {
        return null;
    }
=======
    @Autowired
    private OrderTMapper orderTMapper;
>>>>>>> 4df716c1555bd3a02cbbe3b09f8622dea91849f5
}
