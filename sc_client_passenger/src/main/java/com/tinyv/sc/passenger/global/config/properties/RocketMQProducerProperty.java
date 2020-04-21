package com.tinyv.sc.passenger.global.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mayue
 * @date 2020/04/20
 */

@ConfigurationProperties(prefix="rocketmq.producer")
@Data
public class RocketMQProducerProperty {

    /** 该应用是否启用生产者 */
    private String isOnOff;
    /** 消息的group */
    private String groupName;
    /** name server 地址  */
    private String namesrvAddr;
    /** 单个消息的最大长度 (KB) */
    private Integer maxMessageSize;
    /** 消息发送超时时间 (ms) */
    private Integer sendMsgTimeout;
    /** 消息发送失败重试次数 */
    private Integer retryTimesWhenSendFailed;

}
