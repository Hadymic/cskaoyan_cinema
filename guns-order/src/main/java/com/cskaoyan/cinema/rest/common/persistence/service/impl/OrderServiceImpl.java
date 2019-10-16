package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTMapper orderTMapper;

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        List<String> seats = orderTMapper.selectSoldSeats(fieldId);
        StringBuilder s = new StringBuilder();
        for (String seat : seats) {
            s.append(",").append(seat);
        }
        return s.substring(1);
    }
}
