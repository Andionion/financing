package cn.brody.financing.util;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 * 发送mq消息
 *
 * @author chenyifu6
 */
@Slf4j
@Component
public class MqUtil {

    private static AmqpTemplate rabbitTemplate;

    @Autowired
    public MqUtil(AmqpTemplate amqpTemplate) {
        rabbitTemplate = amqpTemplate;
    }

    /**
     * 要发送的message属性
     *
     * @param message
     * @return
     */
    private static <M> Message getMessageSend(M message) {
        MessageProperties messageProperties = new MessageProperties();
        // 超时时间设置为1小时
        messageProperties.setExpiration("3600000");
        messageProperties.setContentType(MediaType.APPLICATION_JSON_VALUE);
        return new Message(JSON.toJSONString(message).getBytes(), messageProperties);
    }
}
