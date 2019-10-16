package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.rest.common.exception.OrderExceptionEnum;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.order.PayResultVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;

    @PostMapping("getPayResult")
    public BaseRespVo getPayResult(@NotNull String orderId, Integer tryNums) {
        if (tryNums > 3) {
            throw new GunsException(OrderExceptionEnum.PAYMENT_FAILED);
        }
        return new BaseRespVo(0, new PayResultVo(null, 1, "支付成功"), null);
    }
}
