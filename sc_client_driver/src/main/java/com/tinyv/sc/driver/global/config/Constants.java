package com.tinyv.sc.driver.global.config;

/**
 *
 */
public class Constants {

    public static final int MQ_MSG_RETRY_TIMES = 3;

    public static final String MQ_TOPIC = "Order";

    public enum ORDER_STATUS{
        SENDING, RECEIVED, IN_PROGRESS, CANCELED, DONE
    }

    // 订单类型
    public enum ORDER_TYPE{
        SHARE, NON_SHARE
    }

}
