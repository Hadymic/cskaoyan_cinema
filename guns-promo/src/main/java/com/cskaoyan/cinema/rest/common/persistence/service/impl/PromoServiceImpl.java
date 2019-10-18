package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.cskaoyan.cinema.rest.common.persistence.dao.PromoMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoOrderMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoStockMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.Promo;
import com.cskaoyan.cinema.rest.common.persistence.model.PromoOrder;
import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.cinema.CinemaInfoVo;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
    @Autowired
    private PromoMapper promoMapper;
    @Autowired
    private PromoStockMapper promoStockMapper;
    @Autowired
    private PromoOrderMapper promoOrderMapper;
    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;

    @Override
    public List<GetPromoVo> getPromo(Integer cinemaId) {
        List<GetPromoVo> promos = promoMapper.selectByCinemaId(cinemaId);
        if (cinemaId != null) {
            CinemaInfoVo cinemaInfoVo = cinemaService.selectById(cinemaId);
            for (GetPromoVo promo : promos) {
                promo.setStock(promoStockMapper.selectStockByPromoId(promo.getUuid()));
                promo.setCinemaAddress(cinemaInfoVo.getCinemaAdress());
                promo.setCinemaName(cinemaInfoVo.getCinemaName());
                promo.setImgAddress(cinemaInfoVo.getImgUrl());
            }
            return promos;
        }else {
            Set<Integer> cinemaIds = new HashSet<>();
            for (GetPromoVo promo : promos) {
                cinemaIds.add(promo.getCinemaId());
            }
            Map<Integer, CinemaInfoVo> cinemaInfoMap = cinemaService.selectCinemasById(cinemaIds);
            for (GetPromoVo promo : promos) {
                CinemaInfoVo cinemaInfoVo = cinemaInfoMap.get(promo.getCinemaId());
                promo.setStock(promoStockMapper.selectStockByPromoId(promo.getUuid()));
                promo.setCinemaAddress(cinemaInfoVo.getCinemaAdress());
                promo.setCinemaName(cinemaInfoVo.getCinemaName());
                promo.setImgAddress(cinemaInfoVo.getImgUrl());
            }
            return promos;
        }
    }

    @Override
    public boolean creatOrder(Integer userId, Integer promoId, Integer amount) {
        if (promoStockMapper.selectStockByPromoId(promoId) <= 0) {
            return false;
        }
        String substring = UUID.randomUUID().toString().substring(0, 10);
        String uuid = userId + substring + System.currentTimeMillis();
        Promo promo = promoMapper.selectById(promoId);
        Date date = new Date();
        PromoOrder promoOrder = new PromoOrder(uuid, userId,promo.getCinemaId(), uuid, amount,
                promo.getPrice(), promo.getStartTime(), new Date(), promo.getEndTime());
        Integer insert = promoOrderMapper.insert(promoOrder);

        return insert != 0;
    }
}

