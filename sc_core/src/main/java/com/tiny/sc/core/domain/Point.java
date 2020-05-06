package com.tiny.sc.core.domain;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Point {

    /** 坐标X轴 */
    private int point_X;
    /** 坐标Y轴*/
    private int point_Y;

    public Point(int point_X, int point_Y){
        this.point_X = point_X;
        this.point_Y = point_Y;
    }

}
