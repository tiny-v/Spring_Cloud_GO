package com.tinyv.sc.passenger.domain;
import lombok.NoArgsConstructor;
import lombok.Data;

/**
 * @author mayue
 * @date 2020/04/20
 */
@NoArgsConstructor
@Data
public class Driver {

    private int driverId;
    /** 司机当前位置 */
    private Location location;
    /** 状态 */
    private String status;

}
