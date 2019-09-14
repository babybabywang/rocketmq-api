package dev.rocketmq.producer.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-14 18:07
 * @description 局部顺序消息生产者
 */
@Component
@Slf4j
public class OrderProducer {

    @Resource(name = "order")
    private DefaultMQProducer producer;


    public void send() {
        try {
            for (int i = 0; i < 3; i++) {
                String body =  "1下单成功";
                Message msg = new Message(ORDER_TOPIC, DEV_TAG, DEV_KEY + i, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult send = producer.send(msg, (list, message, args) -> {
                    int queueNum = (int) args;
                    return list.get(queueNum);
                }, 1);
                log.info("result:{}", send);
                String body1 =  "2下单成功";
                Message msg1 = new Message(ORDER_TOPIC, DEV_TAG, DEV_KEY + i, body1.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult send1 = producer.send(msg1, (list, message, args) -> {
                    int queueNum = (int) args;
                    return list.get(queueNum);
                }, 2);
                log.info("result:{}", send1);
            }
        } catch (Exception e) {
            log.error("e", e);
        }
    }

}
