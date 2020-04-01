package com.yby.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Menu;
import com.yby.sys.domain.Role;
import com.yby.sys.mapper.MenuMapper;
import com.yby.sys.mapper.RoleMapper;
import com.yby.sys.service.RoleService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.DataXSelect;
import com.yby.sys.vo.PermissionVo;
import com.yby.sys.vo.RoleVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.yby.sys.utils.TreeNodeBuilder.treeNodeDataRoleMenu;

@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper RoleMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public List<Role> queryAllRoleForList(RoleVo RoleVo) {
        return RoleMapper.queryAllRole(RoleVo);
    }

    /* 后期完成权限管理之后再来改 */
    @Override
    public List<String> queryRoleByUserIdForList(RoleVo RoleVo, Integer userId) {
        List<Role> roles = RoleMapper.queryRoleByUid(RoleVo.getAvailable(), userId);
        List<String> s = new ArrayList<>();
        for (Role role : roles) {
            s.add(role.getRolename());
        }
        return s;
    }

    /* 模糊查询 */
    @Override
    public DataGridView queryAllRole(RoleVo RoleVo) {
        // 开启分页
        Page<Object> page = PageHelper.startPage(RoleVo.getPage(), RoleVo.getLimit());
        // 查询所有的菜单信息
        List<Role> list = this.RoleMapper.queryAllRole(RoleVo);
        // 返回layui格式数据
        return new DataGridView(page.getTotal(), list);
    }

    @Override
    public void addRole(RoleVo RoleVo) {
        this.RoleMapper.insertSelective(RoleVo);
    }

    @Override
    public void updateRole(RoleVo RoleVo) {
        this.RoleMapper.updateByPrimaryKeySelective(RoleVo);
    }

    @Override
    public void deleteRole(Integer roleId) {
        System.out.println(roleId + "=====deleteRole");
        // 删除角色表数据
        this.RoleMapper.deleteByPrimaryKey(roleId);
        // 删除sys_role_menu当中的数据  打破关系
        this.RoleMapper.deleteRoleMenuByRid(roleId);
        // 删除sys_role_user当中的数据  打破关系
        this.RoleMapper.deleteRoleUserByUid(roleId);

    }

    @Override
    public void deleteBatchDelete(Integer[] ids) {
        for (Integer id : ids) {
            System.out.println(id);
            /* 删除菜单 */
            deleteRole(id);
        }
    }

    @Override
    public DataGridView initRoleMenuTreeJson(Integer roleid) {
        Menu menu = new Menu();
        // 只查询可用的菜单
        menu.setAvailable(SysConstast.AVAILABLE_TRUE);
        // 1.查询所有可用的菜单
        List<Menu> queryAllMenu = menuMapper.queryAllMenu(menu);
        // 2.根据角色id 查询当前角色用有的菜单  只查询可用的
        List<Menu> roleMenu = menuMapper.queryMenuByRoleId(SysConstast.AVAILABLE_TRUE, roleid);
        // 3.如果当前角色为超级管理员
        // com.yby.sys.utils.TreeNodeBuilder treeNodeDataRole 调用方法进行重构数据
        // 响应过去
        return treeNodeDataRoleMenu(roleid, queryAllMenu, roleMenu);
    }

    @Override
    public void saveRoleMenu(RoleVo roleVo) {
        Integer rid = roleVo.getRoleid();
        Integer[] ids = roleVo.getIds();

        // 如果传入过来的菜单id为空则为取消全部菜单权限
        if (ids == null) {
            // 在保存前   删除 当前用户的全部菜单
            this.RoleMapper.deleteRoleMenuByRid(rid);
        } else {
            // 在保存前   删除 当前用户的全部菜单
            this.RoleMapper.deleteRoleMenuByRid(rid);
            // 实现保存 // 一对多保存
            for (Integer mid : ids) {
                // 循环菜单id保存
                this.RoleMapper.saveRoleMenu(rid, mid);
            }
        }

    }

    @Override
    public List<DataXSelect> queryPermissionRole() {
        return this.RoleMapper.queryPermissionRole();
    }

    @Override
    public List<DataXSelect> queryPermissionRoleUid(PermissionVo permissionVo) {
        return this.RoleMapper.queryPermissionRoleUid(permissionVo);
    }

    @Override
    public List<DataXSelect> queryRoleByUids(Integer userid) {
        return this.RoleMapper.queryRoleByUids(userid);
    }


}