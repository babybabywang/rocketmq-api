package dev.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeOrderlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerOrderly;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.UnsupportedEncodingException;
import java.util.List;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-14 18:40
 * @description 顺序消费者
 */
@Component
@Slf4j
public class OrderConsumer implements MessageListenerOrderly {


    @PostConstruct
    public void consumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(ORDER_CONSUMER_GROUP);
        consumer.setNamesrvAddr(SINGLE_NAME_SERVER);
        consumer.setMessageListener(this);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(ORDER_TOPIC, DEV_TAG);
        consumer.start();
    }

    @Override
    public ConsumeOrderlyStatus consumeMessage(List<MessageExt> messageExts, ConsumeOrderlyContext consumeOrderlyContext) {
        //设置自动提交
        consumeOrderlyContext.setAutoCommit(true);
        for (MessageExt messageExt : messageExts) {
            try {
                log.info("message:{}", new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return ConsumeOrderlyStatus.SUCCESS;
    }
}
