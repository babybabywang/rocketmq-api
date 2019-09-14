package dev.rocketmq.producer;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
     *
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
     *
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
     *
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
     *
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


    /**
     * 事务消息生产者
     *
     * @return
     * @throws MQClientException
     */
    @Bean("transaction")
    public TransactionMQProducer transactionMQProducer()  {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        TransactionMQProducer transactionMQProducer = new TransactionMQProducer(TRANSACTION_GROUP);
        transactionMQProducer.setExecutorService(executorService);
        transactionMQProducer.setNamesrvAddr(SINGLE_NAME_SERVER);
        return transactionMQProducer;
    }

    /**
     * 顺序生产者
     *
     * @return
     * @throws MQClientException
     */
    @Bean("order")
    public DefaultMQProducer order() throws MQClientException {
        DefaultMQProducer producer = new DefaultMQProducer();
        producer.setNamesrvAddr(SINGLE_NAME_SERVER);
        producer.setProducerGroup(ORDER_PRODUCER_GROUP);
        producer.start();
        return producer;
    }


}
