package com.tiny.sc.core.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author mayue
 * @date 2020/04/20
 */
@NoArgsConstructor
@Data
public class Passenger {

    private String passengerId;

    private String passengerName;

    private Location location;

    private String status;

}
