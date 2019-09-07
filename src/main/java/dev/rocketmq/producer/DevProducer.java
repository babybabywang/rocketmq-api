package dev.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-06 23:36
 * @description 生产者
 */
@Component
@Slf4j
public class DevProducer {
    public void send() {

        try {
            DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
            producer.setNamesrvAddr(SINGLE_NAME_SERVER);
            Message message = new Message(DEV_TOPIC, DEV_TAG, "zhangsanlisiwangwu".getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.start();
            SendResult send = producer.send(message);
            log.info("{}", send);
            producer.shutdown();
        } catch (Exception e) {
            log.error("exception:", e);
        }

    }
}
