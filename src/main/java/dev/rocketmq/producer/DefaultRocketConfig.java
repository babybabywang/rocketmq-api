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
 * @description
 */
@Configuration
public class DefaultRocketConfig {
    @Bean("default")
    public DefaultMQProducer defaultMQProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer(PRODUCER_GROUP);
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.start();
        return producer;
    }

    @Bean("async")
    public DefaultMQProducer asyncProducer() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.setProducerGroup(ASYNC_PRODUCER_GROUP);
        producer.start();
        return producer;
    }

}
