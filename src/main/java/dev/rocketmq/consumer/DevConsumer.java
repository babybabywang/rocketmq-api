package dev.rocketmq.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-06 23:29
 * @description 消费者
 */
@Component
@Slf4j
public class DevConsumer implements MessageListenerConcurrently {

    @PostConstruct
    public void consumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(CONSUMER_GROUP);
        consumer.setNamesrvAddr(SINGLE_NAME_SERVER);
        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
        consumer.subscribe(DEV_TOPIC, DEV_TAG);
        consumer.setMessageListener(this);
        consumer.start();

    }

    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(messages)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        try {
            for (MessageExt message : messages) {
                String body = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
                System.out.println(body);
                //如果重试3次后
                if (message.getReconsumeTimes() == 3) {
                    log.info("重试了:{}次", message.getReconsumeTimes());
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }
//                int a = 1 / 0;
            }
        } catch (Exception e) {
            //每次重试固定1s后
            consumeConcurrentlyContext.setDelayLevelWhenNextConsume(1);
            return ConsumeConcurrentlyStatus.RECONSUME_LATER;
        }
        return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
    }
}
