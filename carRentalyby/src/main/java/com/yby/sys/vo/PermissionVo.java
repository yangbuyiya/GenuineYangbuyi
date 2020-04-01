package com.yby.sys.vo;

import com.yby.sys.domain.Permission;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class PermissionVo extends Permission {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Integer page = 1;
    private Integer limit = 10;

    // 角色
    private Integer[] ids;

    // 权限
    private Long[] pnameIds;

    /* 查询当前用户的 */
    private Integer uid;
}
