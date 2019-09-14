package dev.rocketmq.producer.order;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
            String dateStr = LocalDateTime.now().toString();
            for (int i = 0; i < 10; i++) {
                String body = dateStr + " Hello Test" + i;
                Message msg = new Message(ORDER_TOPIC, DEV_TAG, DEV_KEY + i, body.getBytes(RemotingHelper.DEFAULT_CHARSET));
                SendResult send = producer.send(msg, (list, message, args) -> {
                    int queueNum = (int) args % 4;
                    return list.get(queueNum);
                }, i);
                log.info("result:{}", send);
            }
        } catch (Exception e) {
            log.error("e", e);
        }

    }
}
