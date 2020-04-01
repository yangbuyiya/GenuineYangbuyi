package com.yby.sys.vo;

import com.yby.sys.domain.News;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;
@Getter
@Setter
@ToString
public class NewsVo extends News {
    /* 分页参数 */
    private Integer page;
    private Integer limit;

    // 接收多个id
    private Integer[] ids;

    // 从前端传递到后端的   开始和结束时间
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date startTime;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

}