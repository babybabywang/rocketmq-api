package dev.rocketmq.consumer.pull;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.DefaultMQPullConsumer;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.message.MessageQueue;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 21:05
 * @description pullConsumer的使用
 */
//@Component
@Slf4j
public class DevPullConsumer {
    //Map<key, value>  key为指定的队列，value为这个队列拉取数据的最后位置
    private static final Map<MessageQueue, Long> offseTable = new ConcurrentHashMap<>();

    @PostConstruct
    public void start() throws Exception {
        DefaultMQPullConsumer consumer = new DefaultMQPullConsumer(PULL_CONSUMER_GROUP);
        consumer.setNamesrvAddr(SINGLE_NAME_SERVER);
        consumer.start();
        //从DEV_PULL_TOPIC这个主题去获取所有队列（默认会有4个队列）
        Set<MessageQueue> messageQueues = consumer.fetchSubscribeMessageQueues(DEV_PULL_TOPIC);
        if (CollectionUtils.isEmpty(messageQueues)) {
            log.error("消息队列为空");
            return;
        }
        for (MessageQueue messageQueue : messageQueues) {
            log.info("Consume from the queue:{}", messageQueue);
            SINGLE_MQ:
            while (true) {
                PullResult pullResult = consumer.pullBlockIfNotFound(messageQueue, null, getMessageQueueOffset(messageQueue), 32);
                log.info("pullResult:{}", pullResult);
                putMessageQueueOffset(messageQueue, pullResult.getNextBeginOffset());
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> msgFoundList = pullResult.getMsgFoundList();
                        for (MessageExt messageExt : msgFoundList) {
                            System.out.println(new String(messageExt.getBody()));
                        }
                        break;
                    case NO_NEW_MSG:
                        System.out.println("没有的新的数据");
                        //结束死循环
                        break SINGLE_MQ;
                    case NO_MATCHED_MSG:
                        break;
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
            }
        }
        consumer.shutdown();
    }

    /**
     * 存储最后的offset
     *
     * @param messageQueue
     * @param nextBeginOffset
     */
    private void putMessageQueueOffset(MessageQueue messageQueue, long nextBeginOffset) {
        offseTable.put(messageQueue, nextBeginOffset);
    }

    /**
     * 得到消息队列offset
     *
     * @param messageQueue
     * @return
     */
    private long getMessageQueueOffset(MessageQueue messageQueue) {
        Long offset = offseTable.get(messageQueue);
        if (offset != null) {
            return offset;
        }
        return 0;
    }
}
