package com.yby.sys.controller;

import com.yby.sys.utils.AppFileUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;

/* system页面跳转控制器 */
@Controller
@RequestMapping("sys")
public class SysController {
    /*  --------------------------菜单管理----------------------------- */
    /* 跳转到菜单管理 */
    @RequestMapping("toMenuManager")
    @RequiresPermissions("user:toMenuManager")
    public String toMenuManager() {
        return "system/menu/menuManager";
    }

    /* 跳转到菜单管理左边的菜单树 */
    @RequestMapping("toMenuLeft")
    public String toMenuLeft() {
        return "system/menu/menuLeft";
    }

    /* 跳转到菜单管理右边的用户管理 */
    @RequestMapping("toMenuRight")
    public String toMenuRight() {
        return "system/menu/menuRight";
    }

    /*  --------------------------角色管理----------------------------- */

    /* 跳转到角色管理的页面 */
    @RequestMapping("toRoleManager")
    public String toRoleManager() {
        return "system/role/roleManager";
    }


    /*  --------------------------权限管理---------------------------- */
    /**
     * 跳转到权限管理
     *
     */
    @RequestMapping("toPermissionManager")
    public String toPermissionManager() {
        return "system/permission/permissionManager";
    }

    /**
     * 跳转到权限管理-left
     *
     */
    @RequestMapping("toPermissionLeft")
    public String toPermissionLeft() {
        return "system/permission/permissionLeft";
    }


    /**
     * 跳转到权限管理--right
     *
     */
    @RequestMapping("toPermissionRight")
    public String toPermissionRight() {
        return "system/permission/permissionRight";
    }

    /*  --------------------------用户管理----------------------------- */

    /* 跳转到用户管理的页面 */
    @RequestMapping("toUserManager")
    /* 配置这个 判断用户是否有这个权限 */
    @RequiresPermissions("user:toUserManager")
    public String toUserManager() {
        return "system/user/userManager";
    }

    /* 跳转到个人资料 */
    @RequestMapping("toUserInfoManager")
    public String toUserInfoManager() {
        return "system/user/userInfoManager";
    }

    /*  --------------------------日志管理----------------------------- */

    /* 跳转到日志管理 */
    @RequestMapping("toLogInfoManager")
    public String toLogInfoManager() {
        return "system/logInfo/logInfoManager";
    }

    /*  --------------------------公告管理----------------------------- */

    /* 跳转到公告管理 */
    @RequestMapping("toNewsManager")
    public String toNewsManager() {
        return "system/news/newsManager";
    }

    /*  --------------------------错误页面管理----------------------------- */

    /* 跳转到404*/
    @RequestMapping("NoFind")
    public String NoFind() {
        return "system/exception/404";
    }

    /* 跳转到500  Server exception*/
    @RequestMapping("ServerException")
    public String ServerException() {
        return "system/exception/500";
    }

    /* web Exception Page web异常页面 */
    @RequestMapping("webExceptionPage")
    public String webExceptionPage() {
        return "system/exception/webExceptionPage";
    }
}