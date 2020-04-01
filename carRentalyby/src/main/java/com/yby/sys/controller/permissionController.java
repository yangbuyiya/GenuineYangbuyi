package com.yby.sys.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Permission;
import com.yby.sys.domain.Role;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.PermissionService;
import com.yby.sys.service.RoleService;
import com.yby.sys.utils.*;
import com.yby.sys.vo.PermissionVo;
import com.yby.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author TeouBle
 */
@RestController
@RequestMapping("permission")
public class permissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    /**************** 权限管理开始 ****************/

    /**
     * 查询权限 并且高级查询
     */
    @RequestMapping("loadAllPermission")
    public DataGridView loadAllPermission(PermissionVo permissionVo) {
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        permissionVo.setUid(user.getUserid());
        return this.permissionService.queryAllPermission(permissionVo);
    }


    /**
     * 初始化角色下拉框
     *
     * @return
     */
    @RequestMapping("permissionRole")
    public DataGridView permissionRole() {
        return this.permissionService.queryPermissionRole();
    }


    /**
     * 初始化权限名称下拉框
     *
     * @return
     */
    @RequestMapping("permissionPname")
    public DataGridView permissionPname() {
        return this.permissionService.permissionPname();
    }


    /**
     * 初始化权限编码拉框
     *
     * @return
     */
    @RequestMapping("permissionPresource")
    public DataGridView permissionPresource() {
        return this.permissionService.permissionPresource();
    }


    /**
     * 初始化角色下拉框的 角色
     *
     * @return
     */
    @RequestMapping("permissionRolePid")
    public DataGridView permissionRoleUid(PermissionVo permissionVo) {
        System.out.println(permissionVo.getPid());
        List<DataXSelect> strings = this.roleService.queryPermissionRoleUid(permissionVo);
        return new DataGridView(strings);
    }


    /**
     * \
     * <p>
     * 添加权限 (分配权限)
     *
     * @param permissionVo
     * @return
     */
    @RequestMapping("addPermission")
    public ResultObj addPermission(String type, PermissionVo permissionVo) {
        ResultObj resultObj = null;
        try {
            List<Long> longs = Arrays.asList(permissionVo.getPnameIds());
            List<Integer> integers = Arrays.asList(permissionVo.getIds());
            System.out.println(longs);
            System.out.println(integers);

            // 封装方法进行校验
            resultObj = permissionMethod(longs, integers, type);
            return resultObj;
        } catch (Exception e) {
            e.printStackTrace();
            return resultObj;
        }

    }

    private ResultObj permissionMethod(List<Long> longs, List<Integer> integers, String type) {
        if ("add".equals(type)) {
            // 权限添加
//            this.permissionService.addPermission(permissionVo);
            // 查询 权限编号 id
//            Permission permission = this.permissionService.queryByPresource(permissionVo.getPresource());
//            System.out.println(permission);
            // 这个效率比较低   应该使用mybatis当中的循环添加
            for (Integer integer : integers) {
                for (Long aLong : longs) {
                    this.permissionService.addPermissionRole(integer, aLong);
                }
            }
            return ResultObj.ADD_SUCCESS;
        } else if ("delete".equals(type)) {
            for (Integer integer : integers) {
                for (Long aLong : longs) {
                    this.permissionService.deletePermissionRole(integer, aLong);
                }
            }
        }
        return ResultObj.DELETE_SUCCESS;
    }
}