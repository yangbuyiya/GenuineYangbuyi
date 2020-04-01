package com.yby.sys.controller;

import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Role;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.RoleService;
import com.yby.sys.utils.*;
import com.yby.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 角色管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("Role")
public class RoleController {

    @Autowired
    private RoleService RoleService;

    /* 加载角色列表返回layui表格数据格式 */
    @RequestMapping("loadAllRole")
    public DataGridView loadAllRole(RoleVo RoleVo) {
        return this.RoleService.queryAllRole(RoleVo);
    }

    /* 添加角色 */
    @RequestMapping("addRole")
    public ResultObj addRole(RoleVo RoleVo) {
        try {
            this.RoleService.addRole(RoleVo);
            return ResultObj.ADD_SUCCESS;// 添加成功
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;// 添加失败
        }
    }

    /* 修改角色 */
    @RequestMapping("updateRole")
    public ResultObj updateRole(RoleVo RoleVo) {
        try {
            this.RoleService.updateRole(RoleVo);
            return ResultObj.UPDATE_SUCCESS;// 修改成功
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;// 修改失败
        }
    }


    /**
     * 删除角色
     *
     * @param RoleVo
     * @return
     */
    @RequestMapping("deleteRole")
    public ResultObj deleteRole(RoleVo RoleVo) {
        try {
            this.RoleService.deleteRole(RoleVo.getRoleid());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除角色
     *
     * @param roleVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    public ResultObj deleteBatchDelete(RoleVo roleVo) {
        try {
            this.RoleService.deleteBatchDelete(roleVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 根据roleid查询菜单
     * 加载角色管理分配菜单的json
     * 根据角色id 查询当前角色拥有的菜单
     * @return
     */
    @RequestMapping("initRoleMenuTreeJson")
    public DataGridView initRoleMenuTreeJson(RoleVo roleVo){
        return this.RoleService.initRoleMenuTreeJson(roleVo.getRoleid());
    }

    /**
     * 保存角色和菜单的关系
     * @param roleVo
     * @return
     */
    @RequestMapping("saveRoleMenu")
    public ResultObj saveRoleMenu(RoleVo roleVo){
        try {
            this.RoleService.saveRoleMenu(roleVo);
            return ResultObj.DISPATCH_SUCCESS;
        }catch (Exception e0){
            return ResultObj.DISPATCH_ERROR;
        }

    }
}