package com.yby.sys.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class LogInfo {
    /* 日志 id */
    private Integer id;
    /* 日志 名称 */
    private String loginname;
    /* 日志 ip地址 */
    private String loginip;
    /* 日志 从后台传递到前端的时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date logintime;
}