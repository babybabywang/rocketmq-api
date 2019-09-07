package dev.rocketmq.producer.sync;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 00:43
 * @description 异步发送消息
 */
@Component
@Slf4j
public class AsyncProducer {
    @Autowired
    @Qualifier("async")
    private DefaultMQProducer producer;

    public void asyncSend() throws RemotingException, MQClientException, InterruptedException, UnsupportedEncodingException {
        Message message = new Message(DEV_TOPIC, DEV_TAG, "zhangsanlisiwangwu".getBytes(RemotingHelper.DEFAULT_CHARSET));
        producer.send(message, new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("{}", sendResult);
            }

            @Override
            public void onException(Throwable throwable) {
                log.info("err", throwable);
            }
        });
        //defaultMQProducer.shutdown();
    }
}
