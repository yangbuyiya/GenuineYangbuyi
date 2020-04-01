package com.yby.sys.service;

import com.yby.sys.domain.Permission;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.vo.PermissionVo;

import java.util.List;

public interface PermissionService  {

    /* 根据用户id查询权限 */
    public List<String> queryPermissionByUserId(Integer userid);

    /* 获取权限 */
    List<Permission> list(PermissionVo permissionVo);

    /**
     * 查询所有的权限
     * @param permissionVo
     * @return
     */
    DataGridView queryAllPermission(PermissionVo permissionVo);

    /**
     * 查询角色  根据角色来设置权限好吧
     * @return
     */
    DataGridView queryPermissionRole();

    void addPermission(PermissionVo permissionVo);

    void addPermissionRole(Integer id, Long pid);

    Permission queryByPresource(String presource);

    DataGridView permissionPname();

    DataGridView permissionPresource();

    void deletePermissionRole(Integer integer, Long aLong);
}