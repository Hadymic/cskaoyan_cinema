package com.cskaoyan.cinema.rest.common.persistence.service.impl;


import com.alipay.api.AlipayResponse;
import com.alipay.api.response.AlipayTradePrecreateResponse;
import com.alipay.demo.trade.config.Configs;
import com.alipay.demo.trade.model.ExtendParams;
import com.alipay.demo.trade.model.GoodsDetail;
import com.alipay.demo.trade.model.builder.AlipayTradePrecreateRequestBuilder;
import com.alipay.demo.trade.model.builder.AlipayTradeQueryRequestBuilder;
import com.alipay.demo.trade.model.result.AlipayF2FPrecreateResult;
import com.alipay.demo.trade.model.result.AlipayF2FQueryResult;
import com.alipay.demo.trade.service.AlipayMonitorService;
import com.alipay.demo.trade.service.AlipayTradeService;
import com.alipay.demo.trade.service.impl.AlipayMonitorServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeServiceImpl;
import com.alipay.demo.trade.service.impl.AlipayTradeWithHBServiceImpl;
import com.alipay.demo.trade.utils.ZxingUtils;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.cskaoyan.cinema.service.CinemaService;
import com.cskaoyan.cinema.core.exception.CinemaException;
import com.cskaoyan.cinema.core.exception.GunsExceptionEnum;
import com.cskaoyan.cinema.rest.common.persistence.dao.OrderTMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.OrderT;
import com.cskaoyan.cinema.rest.common.persistence.vo.OrderStatusVo;
import com.cskaoyan.cinema.vo.order.OrderVo;
import com.cskaoyan.cinema.service.FilmService;
import com.cskaoyan.cinema.service.OrderService;
import com.cskaoyan.cinema.service.OssService;
import com.cskaoyan.cinema.vo.BaseRespVo;
import com.cskaoyan.cinema.vo.film.FilmOrderVo;
import com.cskaoyan.cinema.vo.order.OrderMsgVo;
import com.cskaoyan.cinema.vo.order.PayInfoVO;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

@Component
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderTMapper orderTMapper;
    @Reference(interfaceClass = FilmService.class)
    private FilmService filmService;
    @Reference(interfaceClass = OssService.class)
    private OssService ossService;
    @Reference(interfaceClass = CinemaService.class, check = false)
    private CinemaService cinemaService;


    @Autowired
    private Jedis jedis;

    /**
     * author:zt
     * 创建订单
     *
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
        int insert = orderTMapper.insertAllColumn(orderT);
        if (insert == 0) {
            throw new CinemaException(GunsExceptionEnum.SERVER_ERROR);
        }
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

    private void dumpResponse(AlipayResponse response) {
        if (response != null) {
            log.info(String.format("code:%s, msg:%s", response.getCode(), response.getMsg()));
            if (StringUtils.isNotEmpty(response.getSubCode())) {
                log.info(String.format("subCode:%s, subMsg:%s", response.getSubCode(),
                        response.getSubMsg()));
            }
            log.info("body:" + response.getBody());
        }
    }

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

    @Override
    public BaseRespVo getPayInfo(String orderId) {
        // (必填) 商户网站订单系统中唯一订单号，64个字符以内，只能包含字母、数字、下划线，
        // 需保证商户系统端不能重复，建议通过数据库sequence生成，
        OrderT orderT = orderTMapper.selectById(orderId);
        String outTradeNo = orderId;

        // (必填) 订单标题，粗略描述用户的支付目的。如“xxx品牌xxx门店当面付扫码消费”
        String subject = "meeting院线电影消费";

        // (必填) 订单总金额，单位为元，不能超过1亿元
        // 如果同时传入了【打折金额】,【不可打折金额】,【订单总金额】三者,则必须满足如下条件:【订单总金额】=【打折金额】+【不可打折金额】
        String totalAmount = orderT.getOrderPrice().toString();

        // (可选) 订单不可打折金额，可以配合商家平台配置折扣活动，如果酒水不参与打折，则将对应金额填写至此字段
        // 如果该值未传入,但传入了【订单总金额】,【打折金额】,则该值默认为【订单总金额】-【打折金额】
        String undiscountableAmount = "0";

        // 卖家支付宝账号ID，用于支持一个签约账号下支持打款到不同的收款账号，(打款到sellerId对应的支付宝账号)
        // 如果该字段为空，则默认为与支付宝签约的商户的PID，也就是appid对应的PID
        String sellerId = "";

        int number = (int) (orderT.getOrderPrice() / orderT.getFilmPrice());
        // 订单描述，可以对交易或商品进行一个详细地描述，比如填写"购买商品2件共15.00元"
        String body = "购买" + number + "张电影票" + orderT.getOrderPrice() + "元";

        // 商户操作员编号，添加此参数可以为商户操作员做销售统计
        String operatorId = "test_operator_id";

        // (必填) 商户门店编号，通过门店号和商家后台可以配置精准到门店的折扣信息，详询支付宝技术支持
        String storeId = "test_store_id";

        // 业务扩展参数，目前可添加由支付宝分配的系统商编号(通过setSysServiceProviderId方法)，详情请咨询支付宝技术支持
        ExtendParams extendParams = new ExtendParams();
        extendParams.setSysServiceProviderId("2088100200300400500");

        // 支付超时，定义为120分钟
        String timeoutExpress = "60m";

        // 商品明细列表，需填写购买商品详细信息，
        List<GoodsDetail> goodsDetailList = new ArrayList<>();
        // 创建一个商品信息，参数含义分别为商品id（使用国标）、名称、单价（单位为分）、数量，如果需要添加商品类别，详见GoodsDetail
        FilmOrderVo filmT = filmService.selectFilmByFilmId(orderT.getFilmId());

        GoodsDetail goods1 = GoodsDetail.newInstance(filmT.getUuid().toString(), filmT.getFilmName(), orderT.getFilmPrice().intValue(), number);
        // 创建好一个商品后添加至商品明细列表
        goodsDetailList.add(goods1);

        // 创建扫码支付请求builder，设置请求参数
        AlipayTradePrecreateRequestBuilder builder = new AlipayTradePrecreateRequestBuilder()
                .setSubject(subject).setTotalAmount(totalAmount).setOutTradeNo(outTradeNo)
                .setUndiscountableAmount(undiscountableAmount).setSellerId(sellerId).setBody(body)
                .setOperatorId(operatorId).setStoreId(storeId).setExtendParams(extendParams)
                .setTimeoutExpress(timeoutExpress)
                //                .setNotifyUrl("http://www.test-notify-url.com")//支付宝服务器主动通知商户服务器里指定的页面http路径,根据需要设置
                .setGoodsDetailList(goodsDetailList);

        AlipayF2FPrecreateResult result = tradeService.tradePrecreate(builder);
        switch (result.getTradeStatus()) {
            case SUCCESS:
                log.info("支付宝预下单成功: )");

                AlipayTradePrecreateResponse response = result.getResponse();
                dumpResponse(response);

                // 需要修改为运行机器上的路径
                String filePath = String.format("D:\\tmp/qr-%s.png", response.getOutTradeNo());
                log.info("filePath:" + filePath);
                File qrCodeImge = ZxingUtils.getQRCodeImge(response.getQrCode(), 256, filePath);
                String imgPre = ossService.upload(qrCodeImge);
                return new BaseRespVo<>(0, imgPre, new PayInfoVO(orderId, qrCodeImge.getName()), null);

            case FAILED:
                log.error("支付宝预下单失败!!!");
                return new BaseRespVo<>(1, null, "订单支付失败，请稍后重试");

            case UNKNOWN:
                log.error("系统异常，预下单状态未知!!!");
                return new BaseRespVo<>(999, null, "系统出现异常，请联系管理员");

            default:
                log.error("不支持的交易状态，交易返回异常!!!");
                return new BaseRespVo<>(999, null, "系统出现异常，请联系管理员");
        }
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
                throw new CinemaException(GunsExceptionEnum.SERVER_ERROR);

            default:
                log.error("订单号：" + orderId + "，不支持的交易状态，交易返回异常!!!");
                throw new CinemaException(GunsExceptionEnum.SERVER_ERROR);
        }
    }

    @Override
    public String getSoldSeatsByFieldId(Integer fieldId) {
        return orderTMapper.selectSoldSeats(fieldId);
    }

    /**
     * author:zt
     * 判断座位号是否存在
     *
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
            String file = this.getClass().getClassLoader().getResource(seats).getPath();
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
        String[] cinemaSeats = soldSeats.split(",");
        for (String cinemaSeat : cinemaSeats) {
            if (cinemaSeat.contains(seatMsg))
                return false;
        }

        return true;
    }

    /**
     * author:zt
     * 判断座位是否已经售出
     *
     * @param fieldId
     * @param soldSeats
     * @return
     */
    @Override
    public boolean isNotSoldSeats(Integer fieldId, String soldSeats) {
        String seats = orderTMapper.selectSoldSeats(fieldId);
        if (seats == null || "".equals(seats.trim())) {
            return true;
        }
        // 已被购买座位数组
        String[] seatsArr = seats.split(",");
        List<String> seatsList = Arrays.asList(seatsArr);
        // 要购买的座位数组
        String[] soldSeatsArr = soldSeats.split(",");
        for (String soldSeat : soldSeatsArr) {
            if (seatsList.contains(soldSeat))
                return false;
        }
        return true;

    }

    /**
     * Zeng-jz
     *
     * @param nowPage
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public List<OrderVo> getOrderInfo(Integer nowPage, Integer pageSize, Integer userId) {
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
            String time = String.valueOf(orderT.getOrderTime().getTime()/1000);
            orderVo.setOrderTimestamp(time);
            orderVos.add(orderVo);
        }
        return orderVos;

    }
}
