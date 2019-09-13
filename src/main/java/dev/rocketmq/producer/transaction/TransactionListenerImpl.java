package dev.rocketmq.producer.transaction;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-13 20:26
 * @description 事务监听器实现类
 */
@Slf4j
public class TransactionListenerImpl implements TransactionListener {
    /**
     * 执行本地事务
     *
     * @param message 消息体
     * @param arg     回调参数
     * @return
     */
    @Override
    public LocalTransactionState executeLocalTransaction(Message message, Object arg) {
        log.info("执行本地事务，异步执行");
        String callArg = (String) arg;
        log.info("callArg:{}", callArg);
        log.info("message:{}", message);
        //begin tx.begin
        //数据库落空操作
        //tx.commit
        return LocalTransactionState.UNKNOW;
    }

    /**
     * 校验本地事务
     *
     * @param messageExt 消息体
     * @return
     */
    @Override
    public LocalTransactionState checkLocalTransaction(MessageExt messageExt) {
        log.info("回调消息检查");
        return LocalTransactionState.COMMIT_MESSAGE;
    }
}
