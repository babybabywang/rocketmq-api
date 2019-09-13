package dev.rocketmq.consumer;

import dev.rocketmq.constants.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-13 20:42
 * @description 事务消息消费
 */
@Component
@Slf4j
public class TxConsumer implements MessageListenerConcurrently {

    @PostConstruct
    public void txConsumer() throws MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(Constants.TX_CONSUMER_GROUP);
        consumer.setNamesrvAddr(Constants.SINGLE_NAME_SERVER);
        consumer.setMessageModel(MessageModel.CLUSTERING);
        consumer.subscribe(Constants.TX_TOPIC, Constants.DEV_TAG);
        consumer.setMessageListener(this);
        consumer.start();
    }

    @Override
    public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
        if (CollectionUtils.isEmpty(messages)) {
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        }
        try {
            for (MessageExt message : messages) {
                String body = new String(message.getBody(), RemotingHelper.DEFAULT_CHARSET);
                System.out.println(body);
                System.out.println(message.getKeys());
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
