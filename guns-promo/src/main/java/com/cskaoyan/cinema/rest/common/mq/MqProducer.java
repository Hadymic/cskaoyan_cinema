package com.cskaoyan.cinema.rest.common.mq;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.cinema.core.constant.StockLogStatus;
import com.cskaoyan.cinema.rest.common.persistence.dao.StockLogMapper;
import com.cskaoyan.cinema.rest.common.persistence.model.StockLog;
import com.cskaoyan.cinema.service.PromoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.*;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component
@Slf4j
public class MqProducer {
    @Value("${mq.nameserver.address}")
    private String address;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.producergroup}")
    private String groupName;
    @Value("${mq.transactionproducergroup}")
    private String transactiongroup;

    private DefaultMQProducer mqProducer;

    private TransactionMQProducer transactionMQProducer;

    @Autowired
    private PromoService promoService;

    @Autowired
    private StockLogMapper stockLogMapper;

    @PostConstruct
    public void initProducer() {
        mqProducer = new DefaultMQProducer(groupName);
        mqProducer.setNamesrvAddr(address);
        try {
            mqProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("mqProducer启动成功...");

        transactionMQProducer = new TransactionMQProducer(transactiongroup);
        transactionMQProducer.setNamesrvAddr(address);

        try {
            transactionMQProducer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }
        log.info("transactionMQProducer启动成功...");
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                if (arg == null) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                HashMap<String, Object> argsMap = (HashMap<String, Object>) arg;
                Integer promoId = (Integer) argsMap.get("promoId");
                Integer amount = (Integer) argsMap.get("amount");
                Integer userId = (Integer) argsMap.get("userId");
                String stockLogId = (String) argsMap.get("stockLogId");

                boolean flag = false;
                try {
                    flag = promoService.createOrder(promoId, amount, userId, stockLogId);
                } catch (Exception e) {
                    e.printStackTrace();
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                if (!flag) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
                return LocalTransactionState.COMMIT_MESSAGE;
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                String json = new String(msg.getBody());

                HashMap<String, Object> map = JSON.parseObject(json, HashMap.class);

                String stockLogId = (String) map.get("stockLogId");

                StockLog stockLog = stockLogMapper.selectById(stockLogId);
                Integer status = stockLog.getStatus();
                if (StockLogStatus.SUCCESS.getCode() == status) {
                    return LocalTransactionState.COMMIT_MESSAGE;
                } else if (StockLogStatus.FAIL.getCode() == status) {
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                } else {
                    return LocalTransactionState.UNKNOW;
                }
            }
        });
    }

    public boolean asyncDecreaseStock(Integer promoId, Integer amount) {
        HashMap<String, Integer> map = new HashMap<>();
        map.put("promoId", promoId);
        map.put("amount", amount);

        Message message = new Message(topic, JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8));

        SendResult sendResult = null;
        try {
            sendResult = mqProducer.send(message);
        } catch (MQClientException | RemotingException | MQBrokerException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }

        if (sendResult == null) {
            return false;
        }
        SendStatus sendStatus = sendResult.getSendStatus();
        return SendStatus.SEND_OK == sendStatus;
    }

    public boolean transactionCreateOrder(Integer promoId, Integer amount, Integer userId, String stockLogId) {
        HashMap<String, Object> map = new HashMap<>(4);
        map.put("promoId", promoId);
        map.put("amount", amount);
        map.put("userId", userId);
        map.put("stockLogId", stockLogId);

        HashMap<String, Object> argsMap = new HashMap<>(4);
        argsMap.put("promoId", promoId);
        argsMap.put("amount", amount);
        argsMap.put("userId", userId);
        argsMap.put("stockLogId", stockLogId);

        Message message = new Message(topic, JSON.toJSONString(map).getBytes(StandardCharsets.UTF_8));

        TransactionSendResult result = null;
        try {
            result = transactionMQProducer.sendMessageInTransaction(message, argsMap);
        } catch (MQClientException e) {
            e.printStackTrace();
            return false;
        }
        if (result == null) {
            return false;
        }

        LocalTransactionState localTransactionState = result.getLocalTransactionState();
        return LocalTransactionState.COMMIT_MESSAGE.equals(localTransactionState);
    }
}
