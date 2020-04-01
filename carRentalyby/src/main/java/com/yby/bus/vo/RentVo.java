package com.yby.bus.vo;

import com.yby.bus.domain.Car;
import com.yby.bus.domain.Rent;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class RentVo extends Rent {

    /* 分页参数 */
    private Integer page;
    private Integer limit;

    // 从前端传递到后端的   开始和结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;


}