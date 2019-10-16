package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.cskaoyan.cinema.rest.common.persistence.vo.OrderStatusVo;
import com.cskaoyan.cinema.rest.common.persistence.vo.OrderVo;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTMapper orderTMapper;
    @Reference(interfaceClass = FilmService.class, check = false)
    private FilmService filmService;
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        List<String> seats = orderTMapper.selectSoldSeats(fieldId);
        StringBuilder s = new StringBuilder();
        for (String seat : seats) {
            s.append(",").append(seat);
        }
        return s.substring(1);
    }

    /**
     * Zeng-jz
     * @param nowPage
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public Object getOrderInfo(Integer nowPage, Integer pageSize, int userId) {
        Page<OrderT> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(nowPage);
        EntityWrapper<OrderT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("order_user", userId);

        List<OrderT> orderTS = orderTMapper.selectPage(page, entityWrapper);
        List<OrderVo> orderVos = new ArrayList<>();
        for (OrderT orderT : orderTS) {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(orderT.getUuid());
            orderVo.setFilmName(filmService.selectNameById(orderT.getFilmId()));
            orderVo.setFieldTime(cinemaService.selectFieldTimeById(orderT.getFieldId()));
            orderVo.setCinemaName(cinemaService.selectNameById(orderT.getCinemaId()));
            orderVo.setOrderPrice(orderT.getOrderPrice());
            orderVo.setSeatsName(orderT.getSeatsName());
            orderVo.setOrderStatus(OrderStatusVo.get(orderT.getOrderStatus()));
            orderVos.add(orderVo);
        }
        return orderVos;
    }
}
