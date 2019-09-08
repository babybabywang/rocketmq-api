package dev.rocketmq.consumer.pull;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.consumer.MQPullConsumer;
import org.apache.rocketmq.client.consumer.MQPullConsumerScheduleService;
import org.apache.rocketmq.client.consumer.PullResult;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static dev.rocketmq.constants.Constants.*;

/**
 * @author echo huang
 * @version 1.0
 * @date 2019-09-08 21:34
 * @description pullSchduleService
 */
@Slf4j
@Component
public class PullScheduleService {

    @PostConstruct
    public void start() throws Exception {
        final MQPullConsumerScheduleService scheduleService = new MQPullConsumerScheduleService(PULL_CONSUMER_GROUP);

        scheduleService.getDefaultMQPullConsumer().setNamesrvAddr(SINGLE_NAME_SERVER);

        scheduleService.setMessageModel(MessageModel.CLUSTERING);

        scheduleService.registerPullTaskCallback(DEV_PULL_TOPIC, (mq, context) -> {
            MQPullConsumer consumer = context.getPullConsumer();
            log.info("-------------- queueId:{}---------- ", mq.getQueueId());
            try {
                // 获取从哪里拉取
                long offset = consumer.fetchConsumeOffset(mq, false);
                if (offset < 0) {
                    offset = 0;
                }

                PullResult pullResult = consumer.pull(mq, DEV_TAG, offset, 32);
                switch (pullResult.getPullStatus()) {
                    case FOUND:
                        List<MessageExt> list = pullResult.getMsgFoundList();
                        for (MessageExt msg : list) {
                            //消费数据...
                            System.out.println(new String(msg.getBody()));
                        }
                        break;
                    case NO_MATCHED_MSG:
                        break;
                    case NO_NEW_MSG:
                    case OFFSET_ILLEGAL:
                        break;
                    default:
                        break;
                }
                //更新下一次拉取的offset位置
                consumer.updateConsumeOffset(mq, pullResult.getNextBeginOffset());
                // 设置再过3000ms后重新拉取
                context.setPullNextDelayTimeMillis(3000);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        scheduleService.start();
    }
}
