package com.tinyv.sc.driver.global.config.properties;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author mayue
 * @date 2020/04/20
 */

@ConfigurationProperties(prefix="rocketmq.consumer")
@Data
public class RocketMQConsumerProperty {

    /** 该应用是否启用生产者 */
    private String isOnOff;
    /** 消息的group */
    private String groupName;
    /** name server 地址  */
    private String namesrvAddr;

    private String topics;

    private Integer consumeThreadMin;

    private Integer consumeThreadMax;

    private Integer consumeMessageBatchMaxSize;

}
