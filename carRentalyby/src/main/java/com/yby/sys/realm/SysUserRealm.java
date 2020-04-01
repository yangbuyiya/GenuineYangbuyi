package com.yby.sys.realm;

import com.yby.sys.domain.Role;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.MenuService;
import com.yby.sys.service.PermissionService;
import com.yby.sys.service.RoleService;
import com.yby.sys.service.UserService;
import com.yby.sys.utils.ActiveUser;
import com.yby.sys.vo.MenuVo;
import com.yby.sys.vo.RoleVo;
import com.yby.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.DigestUtils;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/**
 * realm认证
 * AuthorizingRealm
 */
public class SysUserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userServicel;

    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;
//    private


    /**
     * 认证
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("来到认证=======================");
        String username = (String) authenticationToken.getPrincipal();
        System.out.println("获取用户名:" + username);
        /* 查询用户 */
        SysUser sysUserLogin = userServicel.queryByLoginName(username);
        System.out.println(sysUserLogin);
        if (sysUserLogin != null) {

            // 获取角色
            RoleVo userVo = new RoleVo();
            userVo.setAvailable(1);

            // 根据用户id 查询对应的角色
           List<String> roles =  this.roleService.queryRoleByUserIdForList(userVo,sysUserLogin.getUserid());
            // 获取权限
            // 根据用户id 关联三张表进行查询 权限
            List<String> permissions = this.permissionService.queryPermissionByUserId(sysUserLogin.getUserid());

            // 封装角色 和 权限
            ActiveUser activeUser = new ActiveUser(sysUserLogin, roles, permissions);

            // 来炒菜 加盐
            ByteSource credntialsSalt = ByteSource.Util.bytes(sysUserLogin.getLoginname()+sysUserLogin.getAddress());
            // 进行认证
            SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(activeUser,sysUserLogin.getPwd(),credntialsSalt,this.getName());
            return info;
        } else {
            return null;
        }
    }

    /**
     * 授权
     * 什么情况下会调用授权
     * 1.发现访问路径对有的方法上面有 授权注解 就会调用
     * 2.当jsp当中有授权标签 也会调用
     * @param
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection token) {
        /* 添加权限 */
        System.out.println("授权调用==============");

        // 由于我们已经登录进来了 就可以获取到 javaBean当中的所有信息  登录从数据库当中查询出来的
        ActiveUser activeUser = (ActiveUser) token.getPrimaryPrincipal();
        List<String> roles = activeUser.getRoles();
        List<String> permissions = activeUser.getPermissions();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();


        if(activeUser.getUser().getType() == 1) {
            permissions.add("*:*"); // 全部权限
            info.addStringPermissions(permissions);
            System.out.println("您是超级管理员即可拥有所有权限");
        }else{
            if(null != permissions && permissions.size() > 0){
                /* 添加权限 */
                info.addStringPermissions(permissions);
                System.out.println("开始添加权限");
            }
            System.out.println("您当前拥有的权限:"+permissions);
            if(null != roles && roles.size() > 0){
                /* 添加角色 */
                info.addRoles(roles);
                System.out.println("开始添加角色");
            }
            System.out.println("您当前拥有的权限:"+permissions);
        }


        System.out.println("授权调用调用完毕");

        return info;
    }
}