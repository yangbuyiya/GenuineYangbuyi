package com.yby.bus.vo;

import com.yby.bus.domain.Car;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author TeouBle
 */
@Getter
@Setter
@ToString
public class CarVo extends Car {

    /* 分页参数 */
    private Integer page;
    private Integer limit;

    private String startTime;
    private String endTime;
    // 接收多个id
    private String[] carnumbers;


}