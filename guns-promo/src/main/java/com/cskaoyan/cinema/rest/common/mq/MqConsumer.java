package com.cskaoyan.cinema.rest.common.mq;

import com.alibaba.fastjson.JSON;
import com.cskaoyan.cinema.rest.common.persistence.dao.PromoStockMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class MqConsumer {
    @Value("${mq.nameserver.address}")
    private String address;
    @Value("${mq.topic}")
    private String topic;
    @Value("${mq.consumergroup}")
    private String groupName;

    private DefaultMQPushConsumer mqPushConsumer;

    @Autowired
    private PromoStockMapper promoStockMapper;

    @PostConstruct
    public void initConsumer() {
        mqPushConsumer = new DefaultMQPushConsumer(groupName);
        mqPushConsumer.setNamesrvAddr(address);

        try {
            mqPushConsumer.subscribe(topic, "*");
        } catch (MQClientException e) {
            e.printStackTrace();
            log.error("mqPushConsumer订阅失败");
        }

        mqPushConsumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                MessageExt messageExt = msgs.get(0);
                String json = new String(messageExt.getBody());
                HashMap<String, Integer> map = JSON.parseObject(json, HashMap.class);
                Integer promoId = map.get("promoId");
                Integer amount = map.get("amount");

                Integer affectedRows = promoStockMapper.decreaseStock(promoId, amount);
                if (affectedRows < 1) {
                    log.info("消费失败！扣减库存失败，promoId:{},amount:{}", promoId, amount);
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        try {
            mqPushConsumer.start();
        } catch (MQClientException e) {
            e.printStackTrace();
        }

        log.info("mqPushConsumer启动成功...");
    }
}
