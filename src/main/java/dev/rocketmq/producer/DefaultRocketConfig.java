package dev.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static dev.rocketmq.constants.Constants.*;


/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 00:47
 * @description rocketmq配置
 */
@Configuration
public class DefaultRocketConfig {
    /**
     * 默认生产者
     * @return
     * @throws MQClientException
     */
    @Bean("default")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.start();
        return producer;
    }

    /**
     * 异步生产者
     * @return
     * @throws MQClientException
     */
    @Bean("async")
    public DefaultMQProducer asyncProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.setProducerGroup(ASYNC_PRODUCER_GROUP);
        producer.start();
        return producer;
    }

    /**
     * 延迟生产者
     * @return
     * @throws MQClientException
     */
    @Bean("delay")
    public DefaultMQProducer delayProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.setProducerGroup(DELAY_PRODUCER_GROUP);
        producer.start();
        return producer;
    }

    /**
     * 规则生产者
     * @return
     * @throws MQClientException
     */
    @Bean("rule")
    public DefaultMQProducer ruleProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.setProducerGroup(RULE_PRODUCER_GROUP);
        producer.start();
        return producer;
    }


}
