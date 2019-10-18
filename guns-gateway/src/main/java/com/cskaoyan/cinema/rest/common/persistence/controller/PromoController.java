package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.rest.common.exception.PromoExceptionEnum;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("promo")
public class PromoController {
    @Reference(interfaceClass = PromoService.class)
    private PromoService promoService;
    @Autowired
    private JedisUtils jedisUtils;

    /**
     * Zeng-jz
     * 根据影院id查询秒杀订单列表
     * @param cinemaId
     * @return
     */
    @RequestMapping("getPromo")
    public BaseRespVo getPromo(Integer cinemaId){
        List<GetPromoVo> getPromoVo = promoService.getPromo(cinemaId);
        if (getPromoVo == null){
            throw new GunsException(PromoExceptionEnum.FILM_NOT_FOUND);
        }
        return new BaseRespVo<>(0, getPromoVo, null);
    }

    /**
     * Zeng-jz
     * 秒杀下单接口 创建订单（需要先登录）
     * @param promoId
     * @param amount
     * @param request
     * @return
     */
    @RequestMapping("creatOrder")
    public BaseRespVo creatOrder(@NotNull Integer promoId,@NotNull Integer amount, HttpServletRequest request){
        Integer userId = jedisUtils.getUserId(request);
        boolean flag = promoService.creatOrder(userId, promoId, amount);
        if (flag) {
            return new BaseRespVo<>(0, null, "下单成功");
        }else {
            throw new GunsException(PromoExceptionEnum.FAIL_TO_ORDER);
        }
    }

    @RequestMapping("publishPromoStock")
    public BaseRespVo publishPromoStock(Integer cinemaId){

        return new BaseRespVo();
    }
}
