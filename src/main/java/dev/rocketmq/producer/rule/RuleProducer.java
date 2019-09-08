package dev.rocketmq.producer.rule;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import static dev.rocketmq.constants.Constants.DEV_TAG;
import static dev.rocketmq.constants.Constants.DEV_TOPIC;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 15:48
 * @description 发送特定规则的消息
 */
@Component
@Slf4j
public class RuleProducer {


    @Resource(name = "rule")
    private DefaultMQProducer producer;

    public void ruleSend() {
        try {
            Message message = new Message(DEV_TOPIC, DEV_TAG, "rule message".getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message, (list, msg, arg) -> {
                Integer queueNumber = (Integer) arg;
                log.info("msg:{}", msg);
                //fixme 这里是发送到指定的第2个队列里,正常业务可以基于bizId做分片
                return list.get(queueNumber);
            }, 1);
            log.info("SENDRESULT:{}", sendResult);
        } catch (Exception e) {
            log.error("ERROR:", e);
        }
    }
}
