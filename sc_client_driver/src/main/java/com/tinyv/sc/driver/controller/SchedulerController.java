package com.tinyv.sc.driver.controller;

import com.tiny.sc.core.utils.LocationUtils;
import com.tiny.sc.core.utils.UUIDUtils;
import com.tiny.sc.core.domain.Driver;
import com.tinyv.sc.driver.core.OrderLogic;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import javax.annotation.PostConstruct;

/**
 * @author mayue
 */
@Configuration
@EnableScheduling
public class SchedulerController {

    private int count = 0;

    // 添加定时任务
    @Scheduled(fixedRate=3000)
    public void calcFreeDriver() {
        StringBuilder sb = new StringBuilder();
        sb.append("一共接收了:").append(OrderLogic.total_order_num.get()).append("单,")
                .append(" 一共处理了:").append(OrderLogic.finished_order_num.get()).append("单,")
                .append(" 剩余:").append(OrderLogic.orderMap.size()).append("单,")
                .append(" 正在处理:").append(OrderLogic.in_process_order_num.get()).append("单,")
                .append(" 共创建:").append(count).append("个司机,")
                .append(" 空闲司机:").append(OrderLogic.driversQueue.size());
        System.out.println(sb.toString());
    }

    /**
     * 初始化一批司机
     */
    @PostConstruct
    public void initDriver(){
        Driver driver = null;
        for(int i=0; i<1000; i++){
            driver = new Driver();
            driver.setName("I"+(++count));
            driver.setDriverId(UUIDUtils.getUUID32());
            driver.setLocation(LocationUtils.getRanDomLocation());
            driver.setStatus("FREE");
            OrderLogic.driversQueue.add(driver);
        }
    }



}
