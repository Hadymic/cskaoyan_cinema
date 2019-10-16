package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.order.OrderVo;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderTMapper orderTMapper;

    @Override
    public BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        String[] seats = soldSeats.split(",");
        int length = seats.length;
        OrderT orderT=orderTMapper.queryOrderMsg(fieldId);
        UUID uuid = UUID.randomUUID();
        String uuid1 = uuid.toString().replace("-", "");
        String substring = uuid1.substring(0, 18);
        orderT.setUuid(substring);
        orderT.setSeatsName(seatsName);
         orderT.setSeatsIds(soldSeats);
          Integer price=orderTMapper.queryFilmPrice(fieldId);
        String s = price.toString();
        Double.valueOf(s.toString());
        orderT.setFilmPrice(Double.valueOf(s.toString()));
          orderT.setOrderPrice((double) (price*length));
          orderT.setOrderTime(new Date());
          orderT.setOrderUser(userId);
          orderT.setOrderStatus(0);
          //把数据插入数据库
        boolean flag =  orderTMapper.insertDb(orderT);

        OrderVo orderVo = new OrderVo();
         orderVo.setOrderId(orderT.getUuid());
        Integer filmId = orderT.getFilmId();
        String  filmName=orderTMapper.queryFilmName(filmId);
        orderVo.setFilmName(filmName);
        String fieldTime=orderTMapper.queryFieldTime(fieldId);
        orderVo.setFieldTime(fieldTime);
        String cinemaName=orderTMapper.queryCinema(fieldId);
        orderVo.setCinemaName(cinemaName);
        orderVo.setSeatsName(seatsName);
        orderVo.setOrderPrice(price.toString());
        orderVo.setOrderTimestamp(new Date().getTime()+"");
        return new BaseRespVo(0, orderVo,null);

    }
}
