package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.promo.GetPromoVo;

import java.util.List;

public interface PromoService {
    List<GetPromoVo> getPromo(Integer cinemaId);

    boolean creatOrder(Integer userId, Integer promoId, Integer amount);
}
