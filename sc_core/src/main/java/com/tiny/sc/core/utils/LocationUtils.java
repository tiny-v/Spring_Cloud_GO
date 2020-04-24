package com.tiny.sc.core.utils;

import com.tiny.sc.core.domain.Location;
import com.tiny.sc.core.domain.Point;
import java.util.Random;

/**
 * @author mayue
 * @date 2020/04/20
 */
public class LocationUtils {

    /**
     * @return 返回 [0,10000] 之间的正整数
     */
    private static int getRandomInt(){
        Random r = new Random();
        return r.nextInt(10000);
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
        location.setRegion_X(point.getPoint_X()/100);
        location.setRegion_Y(point.getPoint_Y()/100);
        return location;
    }

    public static void main(String[] args){
        for(int i=0; i<100; i++){
            System.out.println(getRandomInt());
        }
    }

}
