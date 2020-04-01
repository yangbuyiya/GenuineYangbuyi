package com.yby.sys.service;

import com.yby.sys.domain.Role;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.DataXSelect;
import com.yby.sys.vo.PermissionVo;
import com.yby.sys.vo.RoleVo;

import java.util.List;

/*
 * 角色管理服务接口
 * */
public interface RoleService {
    /* 查询所有角色
     * 返回：List<Role> */
    public List<Role> queryAllRoleForList(RoleVo RoleVo);

    /*
     * 问题
     * 不同的用户看到的角色是不一样的
     * 根据用户Id查询用户的可用角色
     * */
    public List<String> queryRoleByUserIdForList(RoleVo RoleVo, Integer userId);

    /* 加载角色列表返回layui表格数据格式 */
    DataGridView queryAllRole(RoleVo RoleVo);

    /* 添加角色 */
    void addRole(RoleVo RoleVo);

    /**
     * 更新角色
     * @param RoleVo
     */
    void updateRole(RoleVo RoleVo);


    /**
     * 根据id删除指定角色
     * @param roleId
     * @return
     */
    void deleteRole(Integer roleId);

    /**
     * 根据数组遍历 删除指定角色
     * @param ids
     * @return
     */
    void deleteBatchDelete(Integer[] ids);
    /**
     * 根据roleid查询菜单
     * 加载角色管理分配菜单的json
     * @return
     */
    DataGridView initRoleMenuTreeJson(Integer roleid);

    /**
     * 保存角色和菜单的关系
     * @param roleVo
     */
    void saveRoleMenu(RoleVo roleVo);


    List<DataXSelect> queryPermissionRole();

    List<DataXSelect> queryPermissionRoleUid(PermissionVo permissionVo);

    List<DataXSelect> queryRoleByUids(Integer userid);
}