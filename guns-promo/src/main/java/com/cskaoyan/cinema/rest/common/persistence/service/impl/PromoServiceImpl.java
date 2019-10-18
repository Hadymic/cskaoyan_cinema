package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.core.exception.CinemaException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;
import com.cskaoyan.cinema.core.util.DateUtil;
import com.cskaoyan.cinema.rest.common.exception.PromoExceptionEnum;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoOrderMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoStockMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.Promo;
import com.cskaoyan.cinema.rest.common.persistence.model.PromoOrder;
import com.cskaoyan.cinema.rest.common.persistence.model.PromoStock;
import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.cinema.CinemaInfoVo;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
    @Reference(interfaceClass = CinemaService.class)
    private CinemaService cinemaService;

    @Autowired
    private PromoMapper promoMapper;

    @Autowired
    private PromoStockMapper promoStockMapper;

    @Autowired
    private PromoOrderMapper promoOrderMapper;

    @Override
    public List<GetPromoVo> getPromo(Integer cinemaId, Integer pageSize, Integer nowPage) {
        List<GetPromoVo> getPromoVos = new ArrayList<>();
        //分页查询秒杀活动
        Date date = new Date();
        Page<Promo> page = new Page<>(nowPage, pageSize, "price", true);
        List<Promo> promos = promoMapper.selectPage(page,
                new EntityWrapper<Promo>()
                        .eq(cinemaId != null, "cinema_id", cinemaId)
                        .eq("status", 1)
                        .le("start_time", date)
                        .ge("end_time", date)
        );
        for (Promo promo : promos) {
            //查询库存stock
            PromoStock promoStock = new PromoStock();
            promoStock.setPromoId(promo.getUuid());
            PromoStock stock = promoStockMapper.selectOne(promoStock);
            //查询影院信息
            CinemaInfoVo cinemaInfoVo = cinemaService.selectCinemaInfoById(promo.getCinemaId());
            //构建返回vo
            GetPromoVo vo = new GetPromoVo(cinemaInfoVo.getCinemaAdress(), promo.getCinemaId(), cinemaInfoVo.getCinemaName(),
                    promo.getDescription(), promo.getEndTime(), cinemaInfoVo.getImgUrl(), promo.getPrice(),
                    promo.getStartTime(), promo.getStatus(), stock.getStock(), promo.getUuid());
            getPromoVos.add(vo);
        }
        return getPromoVos;
    }

    @Override
    public boolean createOrder(Integer promoId, Integer amount, Integer userId) {
        Integer num = checkStock(promoId, amount);
        //查询活动详情
        Promo promo = promoMapper.selectById(promoId);
        //生成唯一兑换码
        String formatId = String.format("%04d", promoId);
        String formatNum = String.format("%06d", num);
        String startTime = DateUtil.formatDate(promo.getStartTime(), "yyyyMMdd");
        String exchangeCode = "EX" + startTime + formatId + formatNum;
        //生成唯一订单id
        String allTime = DateUtil.getAllTime();
        String uuid = UUID.randomUUID().toString().substring(0, 6);
        String id = "OD" + allTime + uuid;
        //构建订单
        PromoOrder promoOrder = new PromoOrder(id, userId, promo.getCinemaId(), exchangeCode,
                amount, promo.getPrice().multiply(new BigDecimal(amount)),
                promo.getStartTime(), new Date(), promo.getEndTime());
        Integer insert = promoOrderMapper.insert(promoOrder);
        return insert == 1;
    }

    /**
     * 校验库存，返回库存量
     *
     * @param promoId
     * @param amount
     * @return
     */
    private Integer checkStock(Integer promoId, Integer amount) {
        PromoStock promoStock = new PromoStock();
        promoStock.setPromoId(promoId);
        PromoStock stock = promoStockMapper.selectOne(promoStock);
        Integer num = stock.getStock();
        //如果库存为零
        if (num <= 0) {
            throw new CinemaException(PromoExceptionEnum.STOCK_ERROR);
        }
        //如果库存小于购买数量
        if (num < amount) {
            throw new CinemaException(PromoExceptionEnum.AMOUNT_ERROR);
        }
        //库存减去购买量
        stock.setStock(num - amount);
        Integer update = promoStockMapper.updateById(stock);
        if (update == 0) {
            throw new CinemaException(GunsExceptionEnum.SERVER_ERROR);
        }
        return num;
    }
}

