package dev.rocketmq.constants;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-06 23:59
 * @description mq常量类
 */
public class Constants {
    public static final String SINGLE_NAME_SERVER = "127.0.0.1:9876";
    public static final String MULTI_NAME_SERVER = "rocketmq1:9876;rocketmq2:9877";
    public static final String DEV_TOPIC = "devTopic";
    public static final String DEV_PULL_TOPIC = "devPullTopic";
    public static final String DEV_TAG = "*";
    public static final String CONSUMER_GROUP = "dev-consumer-group";
    public static final String CLUSTER_CONSUMER_GROUP = "dev-cluster-consumer-group";
    public static final String PULL_CONSUMER_GROUP = "dev-pull-consumer-group";
    public static final String PRODUCER_GROUP = "dev-producer-group";
    public static final String ASYNC_PRODUCER_GROUP = "async-dev-producer-group";
    public static final String DELAY_PRODUCER_GROUP = "delay-dev-producer-group";
    public static final String RULE_PRODUCER_GROUP = "rule-dev-producer-group";
    public static final String ORDER_PRODUCER_GROUP = "order-dev-producer-group";
    public static final String TRANSACTION_GROUP = "transaction-group";
    public static final String DEV_KEY = "key";
    public static final String TX_TOPIC="tx_topic";
    public static final String TX_CONSUMER_GROUP = "tx-consumer-group";
    public static final String ORDER_TOPIC="order_topic";
    public static final String ORDER_CONSUMER_GROUP = "order-consumer-group";
}