package com.tiny.sc.core.utils;

import com.tiny.sc.core.domain.Constants;
import com.tiny.sc.core.domain.Location;
import com.tiny.sc.core.domain.Point;
import java.util.Random;

/**
 * @author mayue
 * @date 2020/04/20
 */
public class LocationUtils {

    private static int getRandomX(){
        Random r = new Random();
        return r.nextInt(Constants.MAP_X);
    }

    private static int getRandomY(){
        Random r = new Random();
        return r.nextInt(Constants.MAP_Y);
    }

    private static String getCurrentRegion(Point point){
        return "R_" + point.getPoint_X()/Constants.MAP_REGION_GAP + "_" + point.getPoint_Y()/Constants.MAP_REGION_GAP;
    }

    /**
     * 模拟一个地点， 包括名称和坐标
     * @return
     */
    public static Location getRanDomLocation(){
        Location location = new Location();
        Point point = new Point(getRandomX(), getRandomY());
        location.setPoint(point);
        location.setRegion_X(point.getPoint_X()/100);
        location.setRegion_Y(point.getPoint_Y()/100);
        location.setRegion(getCurrentRegion(point));
        return location;
    }

}
