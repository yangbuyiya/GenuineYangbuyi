package com.yby.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.sys.domain.Permission;
import com.yby.sys.domain.SysUser;
import com.yby.sys.mapper.PermissionMapper;
import com.yby.sys.service.PermissionService;
import com.yby.sys.service.RoleService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.DataXSelect;
import com.yby.sys.utils.WebUtils;
import com.yby.sys.vo.PermissionVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public List<String> queryPermissionByUserId(Integer userid) {
        return this.permissionMapper.queryPermissionByUserId(userid);
    }

    @Override
    public List<Permission> list(PermissionVo permissionVo) {

        return null;
    }

    @Override
    public DataGridView queryAllPermission(PermissionVo permissionVo) {
        // 开启分页
        Page<Object> page = PageHelper.startPage(permissionVo.getPage(), permissionVo.getLimit());
        // 查询所有的权限信息
        List<Permission> list = this.permissionMapper.queryAllPermission(permissionVo);
        // 返回layui格式数据
        return new DataGridView(page.getTotal(), list);
    }

    @Override
    public DataGridView queryPermissionRole() {
        List<DataXSelect> roles = this.roleService.queryPermissionRole();
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        List<DataXSelect> roleUser = this.roleService.queryRoleByUids(user.getUserid());
         Map<String, Object> map = new HashMap<>();
        map.put("roles",roles);
        map.put("roleUser",roleUser);
        return new DataGridView(map);
    }

    @Override
    public void addPermission(PermissionVo permissionVo) {
        this.permissionMapper.addPermission(permissionVo);
    }

    @Override
    public void addPermissionRole(Integer id, Long pid) {
        this.permissionMapper.addPermissionRole(id,pid);
    }

    @Override
    public Permission queryByPresource(String presource) {
        return this.permissionMapper.queryByPresource(presource);
    }

    @Override
    public DataGridView permissionPname() {
        List<DataXSelect> pname= this.permissionMapper.permissionPname();
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        List<DataXSelect> pnameUser = this.permissionMapper.queryPermissionByUserIdandpname(user.getUserid());
        Map<String, Object> map = new HashMap<>();
        map.put("pname",pname);
        map.put("pnameUser",pnameUser);
        return new DataGridView(map);
    }

    @Override
    public DataGridView permissionPresource() {
        List<DataXSelect> presource= this.permissionMapper.permissionPresource();
        return new DataGridView(presource);
    }

    @Override
    public void deletePermissionRole(Integer integer, Long aLong) {
        this.permissionMapper.deletePermissionRole(integer,aLong);
    }

}