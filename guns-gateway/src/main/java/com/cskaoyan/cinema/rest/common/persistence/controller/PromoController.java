package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.core.exception.CinemaException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;
import com.cskaoyan.cinema.rest.common.exception.BizExceptionEnum;
import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.PageRespVo;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("promo")
public class PromoController {
    @Reference(interfaceClass = PromoService.class)
    private PromoService promoService;

    @Autowired
    private JedisUtils jedisUtils;

    @GetMapping("getPromo")
    public PageRespVo getPromo(Integer cinemaId, Integer pageSize, Integer nowPage) {
        List<GetPromoVo> getPromoVo = promoService.getPromo(cinemaId, pageSize, nowPage);
        return new PageRespVo<>(0, getPromoVo, null, nowPage, (getPromoVo.size() / pageSize) + 1);
    }

    @PostMapping("createOrder")
    public BaseRespVo createOrder(@NotNull Integer promoId, @NotNull Integer amount, HttpServletRequest request) {
        Integer userId = jedisUtils.getUserId(request);
        if (userId == null) {
            throw new CinemaException(BizExceptionEnum.LOGIN_ERROR);
        }
        boolean flag = promoService.createOrder(promoId, amount, userId);
        if (!flag) {
            throw new CinemaException(GunsExceptionEnum.SERVER_ERROR);
        }
        return new BaseRespVo<>(0, null, "下单成功");
    }

    @GetMapping("publishPromoStock")
    public BaseRespVo publishPromoStock() {
        return new BaseRespVo<>(0, null, "发布成功");
    }

    @GetMapping("generateToken")
    public BaseRespVo generateToken() {
        return new BaseRespVo<>(0, UUID.randomUUID().toString().replace("-", ""), null);
    }
}
