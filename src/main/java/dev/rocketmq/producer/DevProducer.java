package dev.rocketmq.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import static dev.rocketmq.constants.Constants.DEV_TAG;
import static dev.rocketmq.constants.Constants.DEV_TOPIC;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-06 23:36
 * @description 生产者
 */
@Component
@Slf4j
public class DevProducer {

    @Autowired
    @Qualifier("default")
    private DefaultMQProducer defaultMQProducer;

    public void send() {

        try {
            Message message = new Message(DEV_TOPIC, DEV_TAG, "zhangsanlisiwangwu".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult send = defaultMQProducer.send(message);
            log.info("{}", send);
            defaultMQProducer.shutdown();
        } catch (Exception e) {
            log.error("exception:", e);
        }

    }
}
