package com.yby.bus.vo;

import com.yby.bus.domain.Customer;
import com.yby.sys.domain.News;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@ToString
public class CustomerVo extends Customer {
    /* 分页参数 */
    private Integer page;
    private Integer limit;

    // 接收多个id
    private String[] ids;


}