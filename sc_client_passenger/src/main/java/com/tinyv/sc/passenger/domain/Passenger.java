package com.tinyv.sc.passenger.domain;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * @author mayue
 * @date 2020/04/20
 */
@NoArgsConstructor
@Data
public class Passenger {

    private int passengerId;

    private int telephone;

    private Location location;

    private String status;

}
