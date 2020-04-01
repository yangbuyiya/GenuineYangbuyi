package com.yby.sys.vo;

import com.yby.sys.domain.Menu;
import com.yby.sys.domain.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter@Setter@ToString
public class RoleVo extends Role {
    /* 分页参数 */
    private Integer page;
    private Integer limit;

    // 批量删除
    private Integer[] ids;

}