package com.tinyv.sc.passenger.utils;

import com.tinyv.sc.passenger.domain.Location;
import com.tinyv.sc.passenger.domain.Point;


/**
 * @author mayue
 * @date 2020/04/20
 */
public class LocationUtils {

    /**
     * @return 返回 [0,10000] 之间的正整数
     */
    private static int getRandomInt(){
        return (int)(Math.random()*10000);
    }

    /**
     * 模拟一个地点， 包括名称和坐标
     * @return
     */
    public static Location getRanDomLocation(){
        Location location = new Location();
        Point point = new Point();
        point.setPoint_X(getRandomInt());
        point.setPoint_Y(getRandomInt());
        location.setPoint(point);
        return location;
    }

    public static void main(String[] args){
        for(int i=0; i<10; i++){
            System.out.println();
        }
    }

}
