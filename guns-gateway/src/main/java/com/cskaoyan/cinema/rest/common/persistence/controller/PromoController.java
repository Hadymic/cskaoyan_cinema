package com.cskaoyan.cinema.rest.common.persistence.controller;

import com.cskaoyan.cinema.consistant.CachePrefixConsistant;
import com.cskaoyan.cinema.core.exception.CinemaException;
import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.rest.common.exception.BizExceptionEnum;
import com.cskaoyan.cinema.rest.common.exception.OrderExceptionEnum;
import com.cskaoyan.cinema.rest.util.JedisUtils;
import com.cskaoyan.cinema.service.PromoService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.PageRespVo;
import com.cskaoyan.cinema.vo.promo.GetPromoVo;
import com.google.common.util.concurrent.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.concurrent.*;

@Slf4j
@RestController
@RequestMapping("promo")
public class PromoController {
    @Reference(interfaceClass = PromoService.class, check = false)
    private PromoService promoService;

    @Autowired
    private JedisUtils jedisUtils;

    @Autowired
    private StringRedisTemplate redisTemplate;

    private ExecutorService executorService;

    private RateLimiter rateLimiter;

    @PostConstruct
    public void init() {
        executorService = Executors.newFixedThreadPool(200);
        rateLimiter = RateLimiter.create(10);
    }

    @GetMapping("getPromo")
    public PageRespVo getPromo(Integer cinemaId, Integer pageSize, Integer nowPage) {
        if (pageSize == null && nowPage == null) {
            pageSize = 12;
            nowPage = 1;
        }
        List<GetPromoVo> getPromoVo = promoService.getPromo(cinemaId, pageSize, nowPage);
        return new PageRespVo<>(0, getPromoVo, null, nowPage, (getPromoVo.size() / pageSize) + 1);
    }

    @PostMapping("createOrder")
    public BaseRespVo createOrder(@NotNull Integer promoId, @NotNull Integer amount, @NotNull String promoToken, HttpServletRequest request) {
        final long startTime = System.currentTimeMillis();

        //利用谷歌guava漏桶算法进行限流
        double acquire = rateLimiter.acquire();
        if (acquire > 0) {
            return new BaseRespVo<>(1, null, "下单失败");
        }

        Integer userId = jedisUtils.getUserId(request);
        if (userId == null) {
            throw new CinemaException(BizExceptionEnum.LOGIN_ERROR);
        }

        //判断库存售罄情况
        String key = CachePrefixConsistant.PROMO_STOCK_NULL_PREFIX + promoId;
        String val = redisTemplate.opsForValue().get(key);
        if (val != null) {
            return new BaseRespVo<>(1, null, "库存已售罄");
        }

        //校验秒杀令牌
        String promoTokenKey = CachePrefixConsistant.PROMO_USER_TOKEN_PREFIX + "userId_" + userId + "_promoId_" + promoId;
        String promoTokenVal = redisTemplate.opsForValue().get(promoTokenKey);
        if (promoTokenVal == null || !promoTokenVal.equals(promoToken)) {
            return new BaseRespVo<>(1, null, "令牌校验失败");
        }

        //使用线程池依靠排队进行队列泄洪
        Future<?> future = executorService.submit(() -> {
            //初始化库存流水，状态是初始化，返回流水id
            String stockLogId = promoService.initPromoStockLog(promoId, amount);
            if (StringUtils.isBlank(stockLogId)) {
                log.info("初始化库存流水失败！promoId:{},amount:{}", promoId, amount);
                throw new GunsException(OrderExceptionEnum.ORDER_ERROR);
            }

            boolean flag = promoService.transactionCreateOrder(promoId, amount, userId, stockLogId);
            if (!flag) {
                log.info("下单失败！promoId:{},userId:{},amount:{}", promoId, userId, amount);
                throw new GunsException(OrderExceptionEnum.ORDER_ERROR);
            }
        });

        try {
            future.get();
        } catch (InterruptedException | ExecutionException | GunsException e) {
            e.printStackTrace();
            return new BaseRespVo<>(1, null, "下单失败");
        }

        final long endTime = System.currentTimeMillis();
        log.info("秒杀下单接口请求耗时 -> {} ms", endTime - startTime);
        return new BaseRespVo<>(0, null, "下单成功");
    }

    @GetMapping("publishPromoStock")
    public BaseRespVo publishPromoStock(Integer cinemaId) {
        //查看是否调用过该接口
        String cache = redisTemplate.opsForValue().get(CachePrefixConsistant.PROMO_CACHE_KEY_IN_REDIS_PREFIX);
        if (StringUtils.isBlank(cache)) {
            boolean flag = promoService.publishPromoStock(cinemaId);
            if (flag) {
                log.info("发布库存到缓存中成功");
                redisTemplate.opsForValue().set(CachePrefixConsistant.PROMO_CACHE_KEY_IN_REDIS_PREFIX, "success");
                redisTemplate.expire(CachePrefixConsistant.PROMO_CACHE_KEY_IN_REDIS_PREFIX, 6, TimeUnit.HOURS);
                return new BaseRespVo<>(0, null, "发布成功");
            } else {
                return new BaseRespVo<>(1, null, "发布失败");
            }
        } else {
            return new BaseRespVo<>(0, null, "已发布");
        }
    }

    @GetMapping("generateToken")
    public BaseRespVo generateToken(@NotNull String promoId, HttpServletRequest request) {
        Integer userId = jedisUtils.getUserId(request);
        if (userId == null) {
            throw new CinemaException(BizExceptionEnum.LOGIN_ERROR);
        }
        //生成秒杀令牌
        String token = promoService.generateToken(promoId, userId);
        if (token == null) {
            return new BaseRespVo<>(1, null, "获取秒杀令牌失败");
        }
        return new BaseRespVo<>(0, null, token);
    }
}
