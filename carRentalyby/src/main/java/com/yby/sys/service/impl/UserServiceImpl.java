package com.yby.sys.service.impl;

import cn.hutool.crypto.digest.MD5;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sun.org.apache.xpath.internal.operations.Bool;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Role;
import com.yby.sys.domain.SysUser;
import com.yby.sys.mapper.RoleMapper;
import com.yby.sys.mapper.SysUserMapper;
import com.yby.sys.service.UserService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.MD5Utils;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.UserVo;
import com.yby.sys.vo.UserVo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public SysUser login(UserVo userVo) {
        // 明文加密
        String s = DigestUtils.md5DigestAsHex(userVo.getPwd().getBytes());
        // 生成密文
        userVo.setPwd(s);
        System.out.println(s + "aaaaaaaaaaaa");
        /* // 明文加密
         *//* 把密码进行加密处理 *//*
        Md5Hash md5Hash = new Md5Hash(userVo.getPwd(), 2);
        *//* 需要toString一下要不然不会进行MD5加密 *//*
        // 生成密文
        System.out.println(md5Hash.toString() + "密文======================");
        userVo.setPwd(md5Hash.toString());*/
        return sysUserMapper.login(userVo);
    }

    @Override
    public DataGridView queryAllUser(UserVo UserVo) {
        Page<Object> page = PageHelper.startPage(UserVo.getPage(), UserVo.getLimit());
        List<SysUser> sysUsers = this.sysUserMapper.queryAllUser(UserVo);
        // 返回分页数据
        return new DataGridView(page.getTotal(), sysUsers);
    }

    @Override
    public void addUser(UserVo UserVo) {
        // 设置默认密码
        UserVo.setPwd(MD5Utils.md5Hash("123456", UserVo.getLoginname() + UserVo.getAddress(), 2));
        // 设置用户类型 都是普通管理员 type = 2
        UserVo.setType(SysConstast.USER_TYPE_NORMAL);
        // 保存用户
        this.sysUserMapper.insertSelective(UserVo);
    }

    @Override
    public void updateUser(UserVo userVo) {
        // 设置用户类型 都是普通管理员 type = 2
        userVo.setType(SysConstast.USER_TYPE_NORMAL);
        this.sysUserMapper.updateByPrimaryKey(userVo);
    }

    @Override
    public void deleteUser(Integer userId) {
        // 删除用户
        this.sysUserMapper.deleteByPrimaryKey(userId);
        // 根据用户id删除 sys role user 数据
        this.roleMapper.deleteRoleUserByUid(userId);
    }

    @Override
    public void deleteBatchDelete(Integer[] ids) {
        for (Integer id : ids) {
            this.deleteUser(id);
        }
    }

    @Override
    public void resetUserPwd(SysUser vo) {
        SysUser sysUser = new SysUser();
        // 设置默认密码
        sysUser.setUserid(vo.getUserid());
        String s = MD5Utils.md5Hash(SysConstast.USER_DEFAULT_PWD, vo.getLoginname() + vo.getAddress(), 2);
        sysUser.setPwd(s);
        // 更新密码
        this.sysUserMapper.updateByPrimaryKeySelective(sysUser);
    }

    @Override
    public DataGridView queryUserRole(Integer userid) {
        // 1.查询所有可用的角色
        Role role = new Role();
        role.setAvailable(SysConstast.AVAILABLE_TRUE);
        List<Role> allRole = this.roleMapper.queryAllRole(role);
        // 2.根据用户id查询已拥有的角色
        List<Role> userRole = this.roleMapper.queryRoleByUid(SysConstast.AVAILABLE_TRUE, userid);
        System.out.println(allRole);
        System.out.println(userRole);
        // 组装数据
        List<Map<String, Object>> data = new ArrayList<>();
        // 循环已拥有的角色
        for (Role role1 : allRole) {
            // 是否有该角色
            Boolean LAY_CHECKED = false;
            for (Role role2 : userRole) {
                if (role1.getRoleid() == role2.getRoleid()) {
                    LAY_CHECKED = true;
                }
            }
            // 组装对象数据
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("roleid", role1.getRoleid());
            map.put("rolename", role1.getRolename());
            map.put("roledesc", role1.getRoledesc());
            map.put("LAY_CHECKED", LAY_CHECKED);
            // 把数据储存起来
            data.add(map);
        }

        return new DataGridView(data);
    }

    @Override
    public void saveUserRole(UserVo userVo) {
        Integer userid = userVo.getUserid();
        Integer[] ids = userVo.getIds();
        // 1.根据用户id删除sys——role——user的数据
        this.roleMapper.deleteRoleUserByUid(userid);
        // 2.根据用户id 保存 sys——role——user的数据
        if (ids != null && ids.length > 0) {
            // 循环整
            for (Integer id : ids) {
                this.sysUserMapper.insertUserRole(userid, id);
            }
        }
    }

    @Override
    public SysUser queryByLoginName(String username) {
        return this.sysUserMapper.queryByLoginName(username);
    }


    @Override
    public List<String> getPermissionById(Integer userid) {
        return this.sysUserMapper.getPermissionById(userid);
    }

    @Override
    public List<String> queryRoleByUserIdForList(Integer userid) {
        return this.sysUserMapper.queryRoleByUserIdForList(userid);
    }


}