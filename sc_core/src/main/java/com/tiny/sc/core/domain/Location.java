package com.tiny.sc.core.domain;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mayue
 * @date 2020/04/20
 */
@Data
@NoArgsConstructor
public class Location {

    /** 地址描述 */
    private String address;
    /** 坐标 */
    private Point point;
    /** 区域 */
    private int region_X;

    private int region_Y;

}


