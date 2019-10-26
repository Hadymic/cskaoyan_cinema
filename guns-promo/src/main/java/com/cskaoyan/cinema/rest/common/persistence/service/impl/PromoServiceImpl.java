package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.consistant.CachePrefixConsistant;
import com.cskaoyan.cinema.core.constant.StockLogStatus;
import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;
import com.cskaoyan.cinema.core.util.DateUtil;
import com.cskaoyan.cinema.rest.common.exception.PromoExceptionEnum;
import com.cskaoyan.cinema.rest.common.mq.MqProducer;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoOrderMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoStockMapper;
import com.cskaoyan.cinema.rest.common.persistence.dao.StockLogMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.Promo;
import com.cskaoyan.cinema.rest.common.persistence.model.PromoOrder;
import com.cskaoyan.cinema.rest.common.persistence.model.PromoStock;
import com.cskaoyan.cinema.rest.common.persistence.model.StockLog;
import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.cinema.CinemaInfoVo;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

@Component
@Slf4j
@Service(interfaceClass = PromoService.class)
public class PromoServiceImpl implements PromoService {
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;

    @Autowired
    private PromoMapper promoMapper;

    @Autowired
    private PromoStockMapper promoStockMapper;

    @Autowired
    private PromoOrderMapper promoOrderMapper;

    @Autowired
    private StockLogMapper stockLogMapper;

    @Autowired
    private MqProducer mqProducer;

    @Autowired
    private StringRedisTemplate redisTemplate;


    private static AtomicInteger num = new AtomicInteger(1);

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

    /**
     * 生成秒杀订单
     * 扣减库存
     *
     * @param promoId
     * @param amount
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public boolean createOrder(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        //查询活动详情
        Promo promo = promoMapper.selectById(promoId);

        //构建订单
        PromoOrder promoOrder = buildPromoOrder(promoId, amount, promo);
        //插入订单
        Integer insert = promoOrderMapper.insert(promoOrder);

        //如果插入失败
        if (insert < 1) {
            log.info("生成秒杀订单失败！promoOrder:{}", JSON.toJSONString(promoOrder));
            //修改库存流水表的status字段为失败
            updateStockLog(stockLogId, StockLogStatus.FAIL);
            throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
        }

        //扣减库存
        boolean flag = decreaseStock(promoId, amount);
        if (!flag) {
            String key = CachePrefixConsistant.PROMO_STOCK_CACHE_PREFIX + promoId;
            Long increment = redisTemplate.opsForValue().increment(key, amount);
            if (increment < 0) {
                log.info("恢复库存失败，promoId:{}", promoId);
                throw new GunsException(GunsExceptionEnum.DATABASE_ERROR);
            }
            //修改库存流水状态为失败
            updateStockLog(stockLogId, StockLogStatus.FAIL);
            throw new GunsException(PromoExceptionEnum.STOCK_ERROR);
        }
        //修改库存流水状态为成功
        updateStockLog(stockLogId, StockLogStatus.SUCCESS);
        return true;
    }

    @Override
    public boolean publishPromoStock(Integer cinemaId) {
        List<GetPromoVo> getPromoVos = new ArrayList<>();
        //查询全部秒杀活动
        Date date = new Date();
        List<Promo> promos = promoMapper.selectList(
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
            //构建返回vo
            GetPromoVo vo = new GetPromoVo(null, promo.getCinemaId(), null,
                    promo.getDescription(), promo.getEndTime(), null, promo.getPrice(),
                    promo.getStartTime(), promo.getStatus(), stock.getStock(), promo.getUuid());
            getPromoVos.add(vo);
        }

        for (GetPromoVo promo : getPromoVos) {
            //把promoId对应的库存存入 redis
            Integer stock = promo.getStock();
            Integer uuid = promo.getUuid();
            String key = CachePrefixConsistant.PROMO_STOCK_CACHE_PREFIX + uuid;
            redisTemplate.opsForValue().set(key, stock + "");

            String tokenAmountKey = CachePrefixConsistant.PROMO_STOCK_AMOUNT_LIMIT + uuid;
            //生成5倍的秒杀令牌
            int amount = stock * 5;
            redisTemplate.opsForValue().set(tokenAmountKey, "" + amount);
        }
        return true;
    }

    @Override
    public boolean transactionCreateOrder(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        return mqProducer.transactionCreateOrder(promoId, amount, userId, stockLogId);
    }

    @Override
    public String initPromoStockLog(Integer promoId, Integer amount) {
        StockLog stockLog = new StockLog();
        stockLog.setStatus(StockLogStatus.INIT.getCode());
        stockLog.setPromoId(promoId);
        stockLog.setAmount(amount);

        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
        stockLog.setUuid(uuid);
        Integer insert = stockLogMapper.insert(stockLog);
        return insert > 0 ? uuid : null;
    }

    @Override
    public String generateToken(String promoId, Integer userId) {
        String token = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 18);
        //扣减秒杀令牌的数量
        String promoAmountKey = CachePrefixConsistant.PROMO_STOCK_AMOUNT_LIMIT + promoId;
        Long res = redisTemplate.opsForValue().increment(promoAmountKey, -1);
        if (res < 0) {
            log.info("秒杀令牌已发放完毕！promoId:{}", promoId);
            return null;
        }
        //存放token到redis中
        String key = CachePrefixConsistant.PROMO_USER_TOKEN_PREFIX + "userId_" + userId + "_promoId_" + promoId;
        redisTemplate.opsForValue().set(key, token);
        return token;
    }

    //构建订单
    private PromoOrder buildPromoOrder(Integer userId, Integer amount, Promo promo) {
        //如果超出一百万则重置
        num.compareAndSet(1000000, 1);
        String formatNum = String.format("%06d", num.getAndIncrement());
        String now = DateUtil.getAllTime();
        //生成唯一兑换码
        String exchangeCode = "EX" + now + formatNum;
        //生成唯一订单id
        String id = "OD" + now + formatNum;
        //构建订单
        return new PromoOrder(id, userId, promo.getCinemaId(), exchangeCode,
                amount, promo.getPrice().multiply(new BigDecimal(amount)).setScale(2, RoundingMode.HALF_UP),
                promo.getStartTime(), new Date(), promo.getEndTime());
    }

    private void updateStockLog(String stockLogId, StockLogStatus stockLogStatus) {
        StockLog stockLog = new StockLog();
        stockLog.setUuid(stockLogId);
        stockLog.setStatus(stockLogStatus.getCode());
        stockLogMapper.updateById(stockLog);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean decreaseStock(Integer promoId, Integer amount) {
        String key = CachePrefixConsistant.PROMO_STOCK_CACHE_PREFIX + promoId;

        Long increment = redisTemplate.opsForValue().increment(key, amount * -1);
        //打上库存售罄的标记
        if (increment == 0) {
            String stockKey = CachePrefixConsistant.PROMO_STOCK_NULL_PREFIX + promoId;
            redisTemplate.opsForValue().set(stockKey, "success");
        }
        if (increment < 0) {
            log.info("库存已售完，promoId:{}", promoId);
            return false;
        }
        return true;
    }
}

