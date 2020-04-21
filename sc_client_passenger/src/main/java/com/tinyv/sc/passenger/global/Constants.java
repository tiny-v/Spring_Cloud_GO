package com.tinyv.sc.passenger.global;

/**
 * @author mayue
 * @date 2020/04/20
 */
public class Constants {

    /**
     * 订单状态
     * SENDING -- 发送
     * RECEIVED -- 已接单,
     * IN_PROGRESS -- 进行中,
     * CANCELED -- 取消,
     * DONE -- 结束
     */
    public enum ORDER_STATUS{
        SENDING, RECEIVED, IN_PROGRESS, CANCELED, DONE
    }

    // 订单类型
    public enum ORDER_TYPE{
        SHARE, NON_SHARE
    }



}
