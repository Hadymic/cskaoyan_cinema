package com.cskaoyan.cinema.rest.common.persistence.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.cinema.CinemaService;
import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.cskaoyan.cinema.rest.common.persistence.vo.OrderStatusVo;
import com.cskaoyan.cinema.rest.common.persistence.vo.OrderVo;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.service.OrderService;
import org.apache.dubbo.config.annotation.Reference;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.cskaoyan.cinema.core.exception.GunsException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;

import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.order.OrderMsgVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;


import java.util.ArrayList;



@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTMapper orderTMapper;
    @Reference(interfaceClass = FilmService.class, check = false)
    private FilmService filmService;
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;


    @Autowired
    private Jedis jedis;

    /**
     * author:zt
     * 创建订单
     * @param fieldId
     * @param soldSeats
     * @param seatsName
     * @param userId
     * @return
     */
    @Override
    public BaseRespVo buyTickets(Integer fieldId, String soldSeats, String seatsName, Integer userId) {
        String[] seats = soldSeats.split(",");
        int length = seats.length;
        OrderT orderT = orderTMapper.queryOrderMsg(fieldId);
        UUID uuid = UUID.randomUUID();
        String uuid1 = uuid.toString().replace("-", "");
        String substring = uuid1.substring(0, 18);
        orderT.setUuid(substring);
        orderT.setSeatsName(seatsName);
        orderT.setSeatsIds(soldSeats);
        Integer price = orderTMapper.queryFilmPrice(fieldId);
        String s = price.toString();
        Double.valueOf(s.toString());
        orderT.setFilmPrice(Double.valueOf(s.toString()));
        orderT.setOrderPrice((double) (price * length));
        orderT.setOrderTime(new Date());
        orderT.setOrderUser(userId);
        orderT.setOrderStatus(0);
        //把数据插入数据库
        boolean flag = orderTMapper.insertDb(orderT);
        OrderMsgVo orderVo = new OrderMsgVo();
        orderVo.setOrderId(orderT.getUuid());
        Integer filmId = orderT.getFilmId();
        String filmName = orderTMapper.queryFilmName(filmId);
        orderVo.setFilmName(filmName);
        String fieldTime = orderTMapper.queryFieldTime(fieldId);
        orderVo.setFieldTime(fieldTime);
        String cinemaName = orderTMapper.queryCinema(fieldId);
        orderVo.setCinemaName(cinemaName);
        orderVo.setSeatsName(seatsName);
        orderVo.setOrderPrice(price.toString());
        orderVo.setOrderTimestamp(new Date().getTime() + "");
        return new BaseRespVo(0, orderVo, "下单成功");
    }

    private static Log log = LogFactory.getLog(OrderServiceImpl.class);

    // 支付宝当面付2.0服务
    private static AlipayTradeService tradeService;

    // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
    private static AlipayTradeService tradeWithHBService;

    // 支付宝交易保障接口服务，供测试接口api使用，请先阅读readme.txt
    private static AlipayMonitorService monitorService;

    static {
        /** 一定要在创建AlipayTradeService之前调用Configs.init()设置默认参数
         *  Configs会读取classpath下的zfbinfo.properties文件配置信息，如果找不到该文件则确认该文件是否在classpath目录
         */
        Configs.init("zfbinfo.properties");

        /** 使用Configs提供的默认参数
         *  AlipayTradeService可以使用单例或者为静态成员对象，不需要反复new
         */
        tradeService = new AlipayTradeServiceImpl.ClientBuilder().build();

        // 支付宝当面付2.0服务（集成了交易保障接口逻辑）
        tradeWithHBService = new AlipayTradeWithHBServiceImpl.ClientBuilder().build();

        /** 如果需要在程序中覆盖Configs提供的默认参数, 可以使用ClientBuilder类的setXXX方法修改默认参数 否则使用代码中的默认设置 */
        monitorService = new AlipayMonitorServiceImpl.ClientBuilder()
                .setGatewayUrl("http://mcloudmonitor.com/gateway.do").setCharset("GBK")
                .setFormat("seats").build();
    }

    /**
     * 获取支付结果
     *
     * @param orderId
     * @return
     * @author hadymic
     */
    @Override
    public boolean getPayResult(String orderId) {
        // 创建查询请求builder，设置请求参数
        AlipayTradeQueryRequestBuilder builder = new AlipayTradeQueryRequestBuilder()
                .setOutTradeNo(orderId);

        AlipayF2FQueryResult result = tradeService.queryTradeResult(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("订单号：" + orderId + "，查询该订单支付成功!");
                //更新数据库订单状态
                OrderT order = new OrderT();
                order.setUuid(orderId);
                order.setOrderStatus(1);
                orderTMapper.updateById(order);
                return true;

            case FAILED:
                log.error("订单号：" + orderId + "，查询该订单支付失败或被关闭!!!");
                return false;

            case UNKNOWN:
                log.error("订单号：" + orderId + "，系统异常，订单支付状态未知!!!");
                throw new GunsException(GunsExceptionEnum.SERVER_ERROR);

            default:
                log.error("订单号：" + orderId + "，不支持的交易状态，交易返回异常!!!");
                throw new GunsException(GunsExceptionEnum.SERVER_ERROR);
        }
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        List<String> seats = orderTMapper.selectSoldSeats(fieldId);
        StringBuilder s = new StringBuilder();
        for (String seat : seats) {
            s.append(",").append(seat);
        }
        return s.substring(1);


    }

    /**
     * author:zt
     * 判断座位号是否存在
     * @param fieldId
     * @param soldSeats
     * @return
     * @throws IOException
     */
    @Override
    public boolean isTrueSeats(Integer fieldId, String soldSeats) throws IOException {
        String seats = orderTMapper.getSeatMsg(fieldId);
        String seatsIds = jedis.get(seats);
        if (seatsIds == null) {
            String file = this.getClass().getResource(seats).getFile();
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String tmp;
            while ((tmp = reader.readLine()) != null) {
                if (tmp.contains("ids")) {
                    tmp = tmp.replace(" ", "");
                    int index = tmp.indexOf(":");
                    seatsIds = tmp.substring(index + 2, tmp.length() - 2);
                    jedis.set(seats, seatsIds);
                    break;
                }
            }
        }
        String seatMsg = jedis.get(seats);
        String[]cinemaSeats = soldSeats.split(",");
        for (String cinemaSeat : cinemaSeats) {
               if(cinemaSeat.contains(seatMsg))
                   return  false;
        }

        return true;
    }

    /**
     * author:zt
     * 判断座位是否已经售出
     * @param fieldId
     * @param soldSeats
     * @return
     */
    @Override
    public boolean isNotSoldSeats(Integer fieldId, String soldSeats) {
        List<String> seats = orderTMapper.selectSoldSeats(fieldId);
        StringBuilder s = new StringBuilder();
        for (String seat : seats) {
            s.append(",").append(seat);
        }
        String soldSeatMsgs = s.substring(1);
        String[] strings = soldSeats.split(",");
        for (String string : strings) {
            if(soldSeatMsgs.contains(string))
                return  false;
        }
        return true;

    }

    /**
     * Zeng-jz
     * @param nowPage
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public Object getOrderInfo(Integer nowPage, Integer pageSize, int userId) {
        Page<OrderT> page = new Page<>();
        page.setSize(pageSize);
        page.setCurrent(nowPage);
        EntityWrapper<OrderT> entityWrapper = new EntityWrapper<>();
        entityWrapper.eq("order_user", userId);

        List<OrderT> orderTS = orderTMapper.selectPage(page, entityWrapper);
        List<OrderVo> orderVos = new ArrayList<>();
        for (OrderT orderT : orderTS) {
            OrderVo orderVo = new OrderVo();
            orderVo.setOrderId(orderT.getUuid());
            orderVo.setFilmName(filmService.selectNameById(orderT.getFilmId()));
            orderVo.setFieldTime(cinemaService.selectFieldTimeById(orderT.getFieldId()));
            orderVo.setCinemaName(cinemaService.selectNameById(orderT.getCinemaId()));
            orderVo.setOrderPrice(orderT.getOrderPrice());
            orderVo.setSeatsName(orderT.getSeatsName());
            orderVo.setOrderStatus(OrderStatusVo.get(orderT.getOrderStatus()));
            orderVos.add(orderVo);
        }
        return orderVos;

    }
}
