package com.tiny.sc.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mayue
 * @date 2020/04/20
 */
@NoArgsConstructor
@Data
public class Driver {

    private String driverId;
    /** 司机名称 */
    private String name;
    /** 司机当前位置 */
    private Location location;
    /** 状态 */
    private String status;

    private Order grabOrder;

}
