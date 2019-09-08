package dev.rocketmq.producer.delay;

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
 * @date 2019-09-08 15:42
 * @description 延迟队列生产者
 */
@Component
@Slf4j
public class DelayProducer {

    @Autowired
    @Qualifier("delay")
    private DefaultMQProducer producer;

    public void delaySend() {
        try {
            Message message = new Message(DEV_TOPIC, DEV_TAG, "delay message".getBytes(RemotingHelper.DEFAULT_CHARSET));
            //延迟5s
            message.setDelayTimeLevel(5);
            SendResult sendResult = producer.send(message);
            log.info("sendResult:{}", sendResult);
        } catch (Exception e) {
            log.error("ERROR:", e);
        }

    }
}

