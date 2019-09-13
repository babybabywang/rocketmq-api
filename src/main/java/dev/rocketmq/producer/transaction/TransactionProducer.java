package dev.rocketmq.producer.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-13 20:15
 * @description 事务消息提供者
 */
@Component
@Slf4j
public class TransactionProducer {

    @Resource(name = "transaction")
    private TransactionMQProducer transactionMQProducer;


    public void txSend() {
        try {
            //1、异步执行本地事务 2、消息回查
            TransactionListener transactionListener = new TransactionListenerImpl();
            transactionMQProducer.setTransactionListener(transactionListener);
            transactionMQProducer.start();
            Message message = new Message(TX_TOPIC, DEV_TAG, DEV_KEY, "hahaha".getBytes(RemotingHelper.DEFAULT_CHARSET));
            transactionMQProducer.sendMessageInTransaction(message, "我是回调的参数");
        } catch (MQClientException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

}
