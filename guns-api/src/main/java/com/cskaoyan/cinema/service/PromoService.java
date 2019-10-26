package com.cskaoyan.cinema.service;

import com.cskaoyan.cinema.vo.promo.GetPromoVo;

import java.util.List;

public interface PromoService {
    /**
     * 分页查询秒杀活动
     *
     * @param cinemaId
     * @param pageSize
     * @param nowPage
     * @return
     */
    List<GetPromoVo> getPromo(Integer cinemaId, Integer pageSize, Integer nowPage);

    boolean createOrder(Integer promoId, Integer amount, Integer userId, String stockLogId);

    boolean publishPromoStock(Integer cinemaId);

    boolean transactionCreateOrder(Integer promoId, Integer amount, Integer userId, String stockLogId);

    String initPromoStockLog(Integer promoId, Integer amount);

    String generateToken(String promoId, Integer userId);
}
