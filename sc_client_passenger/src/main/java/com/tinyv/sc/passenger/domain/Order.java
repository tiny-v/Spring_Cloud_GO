package com.tinyv.sc.passenger.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mayue
 * @date 2020/04/20
 */
@Data
@NoArgsConstructor
public class Order {

    /**订单ID*/
    private String orderId;
    /**乘客电话号码*/
    private int phoneNumber;
    /**出发地址*/
    private Location startPoint;
    /**目的地*/
    private Location endPoint;
    /**订单状态 - 发单中 | 已接单 | 进行中 | 取消 | 结束*/
    private String status;
    /**订单类型 - 拼车 | 不拼车*/
    private String type;
    /**创建时间*/
    private String createTime;
    /**更新时间*/
    private String updateTime;


}
