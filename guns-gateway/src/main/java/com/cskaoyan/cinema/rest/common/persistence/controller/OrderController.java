package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;
import com.cskaoyan.cinema.rest.common.exception.OrderExceptionEnum;
import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.order.PayResultVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("order")
public class OrderController {
    @Reference(interfaceClass = OrderService.class)
    private OrderService orderService;
    @Autowired
    private JedisUtils jedisUtils;

    @PostMapping("order/buyTickets")
    public BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName, HttpServletRequest request) {
        Integer userId = jedisUtils.getUserId(request);
        BaseRespVo baseRespVo = orderService.buyTickets(fieldId, soldSeats, seatsName, userId);
        return baseRespVo;
    }

    /**
     * 获取支付结果
     *
     * @param orderId
     * @param tryNums
     * @return
     * @author hadymic
     */
    @PostMapping("getPayResult")
    public BaseRespVo getPayResult(String orderId, Integer tryNums) {
        if (orderId == null || "".equals(orderId) || tryNums == null || tryNums <= 0) {
            throw new GunsException(GunsExceptionEnum.REQUEST_NULL);
        }
        //尝试次数超过3，订单支付失败
        if (tryNums > 3) {
            throw new GunsException(OrderExceptionEnum.PAYMENT_FAILED);
        }
        //获取订单支付状态
        boolean flag = orderService.getPayResult(orderId);
        if (flag) {
            return new BaseRespVo<>(0, new PayResultVo(null, 1, "支付成功"), null);
        } else {
            return new BaseRespVo<>(1, null, "支付失败！");
        }
    }

<<<<<<< HEAD
    @PostMapping("getPayInfo")
    public BaseRespVo getPayInfo(String orderId) {
        BaseRespVo baseRespVo = orderService.getPayInfo(orderId);
        return baseRespVo;
=======
    /**
     * Zeng-jz
     * 获取用户订单信息
     * @param nowPage
     * @param pageSize
     * @return
     */
    @PostMapping("getOrderInfo")
    public BaseRespVo getOrderInfo(@NotNull Integer nowPage,@NotNull Integer pageSize){
        int userId = 1;
        Object data = orderService.getOrderInfo(nowPage, pageSize, userId);
        if (data != null){
            throw new GunsException(OrderExceptionEnum.ORDER_EMPTY);
        }
        return new BaseRespVo(0, data, null);
>>>>>>> dc3474bf7921386e567846f1c271461bfe896816
    }
}
