package com.yby.sys.vo;

import com.yby.sys.domain.SysUser;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserVo extends SysUser {
    /* 分页参数 */
    private Integer page;
    private Integer limit;

    // 接收多个id
    private Integer[] ids;

    // 验证码
    private String code;

    // 7天免登录
    Integer rememberMe;

}