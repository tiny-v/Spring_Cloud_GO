package com.tiny.sc.core.domain;


/**
 * @author mayue
 */
public class Constants {

    public static Integer MAP_X = 10000;

    public static Integer MAP_Y = 10000;

    public static Integer MAP_REGION_GAP = 2000;

    public static Double DRIVER_SPEED = 2000.0;

    public static String MQ_TOPIC_SENDING_ORDER = "SENDING_ORDER";

    /**
     * 订单状态
     * SENDING -- 发送
     * RECEIVED -- 已接单,
     * IN_PROGRESS -- 进行中,
     * CANCELED -- 取消,
     * DONE -- 结束
     */
    public enum ORDER_STATUS{
        SENDING,
        ACCEPTED,
        IN_PROGRESS,
        CANCELED,
        DONE
    }

    /** 订单类型 */
    public enum ORDER_TYPE{
        SHARE,
        NON_SHARE
    }

    /** 司机状态 */
    public enum DRIVER_STATUS{
        FREE,
        BUSY
    }

    /** 乘客状态 */
    public enum PASSENGER_STATUS{
        /**
         * FREE
         * */
        FREE,
        /**
         * BUSY
         * */
        BUSY
    }


}
