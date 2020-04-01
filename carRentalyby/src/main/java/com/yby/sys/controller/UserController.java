package com.yby.sys.controller;

import com.yby.sys.domain.Role;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.RoleService;
import com.yby.sys.service.UserService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.UserVo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/*
 * 用户管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("User")
public class UserController {

    @Autowired
    private UserService UserService;


    /* 加载用户列表返回layui表格数据格式 */
    @RequestMapping("loadAllUser")
    @RequiresPermissions("user:loadAllUser")
    public DataGridView loadAllUser(UserVo UserVo) {
        return this.UserService.queryAllUser(UserVo);
    }

    /* 添加用户 */
    @RequestMapping("addUser")
    /* 配置这个 判断用户是否有这个权限 */
    @RequiresPermissions("user:addUser")
    public ResultObj addUser(UserVo UserVo) {
        try {
            this.UserService.addUser(UserVo);
            return ResultObj.ADD_SUCCESS;// 添加成功
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;// 添加失败
        }
    }

    /* 修改用户 */
    @RequestMapping("updateUser")
    /* 配置这个 判断用户是否有这个权限 */

    public ResultObj updateUser(UserVo UserVo) {
        try {
            this.UserService.updateUser(UserVo);
            return ResultObj.UPDATE_SUCCESS;// 修改成功
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;// 修改失败
        }
    }


    /**
     * 删除用户
     *
     * @param UserVo
     * @return
     */
    @RequestMapping("deleteUser")
    /* 配置这个 判断用户是否有这个权限 */

    public ResultObj deleteUser(UserVo UserVo) {
        try {
            this.UserService.deleteUser(UserVo.getUserid());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除用户
     *
     * @param userVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    /* 配置这个 判断用户是否有这个权限 */

    public ResultObj deleteBatchDelete(UserVo userVo) {
        try {
            this.UserService.deleteBatchDelete(userVo.getIds());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 重置密码
     *
     * @param userVo
     * @return
     */
    @RequestMapping("resetUserPwd")
    /* 配置这个 判断用户是否有这个权限 */

    public ResultObj resetUserPwd(UserVo userVo) {
        try {
            SysUser sysUser = this.UserService.queryByLoginName(userVo.getLoginname());
            System.out.println(sysUser);
            this.UserService.resetUserPwd(sysUser);
            return ResultObj.RESET_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.RESET_ERROR;
        }
    }

    /**
     * 加载用户管理的分配角色数据
     *
     * @param userVo
     * @return
     */
    @RequestMapping("initUserRole")

    public DataGridView initUserRole(UserVo userVo) {
        return this.UserService.queryUserRole(userVo.getUserid());
    }

    /**
     * 保存用户和角色的关系
     *
     * @param userVo
     * @return
     */
    @RequestMapping("saveUserRole")

    public ResultObj saveUserRole(UserVo userVo) {
        try {
            this.UserService.saveUserRole(userVo);
            return ResultObj.DISPATCH_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DISPATCH_ERROR;
        }
    }

    //    图片上传
    @RequestMapping("uploadImage")
    public void uploadImage(@RequestParam("file") MultipartFile file, HttpServletRequest request) throws IOException {
        System.out.println(file);
        System.out.println(file.getContentType());//文件的类型
        System.out.println(file.getName());//表单的name属性值
        System.out.println(file.getOriginalFilename());//文件名
        System.out.println(file.getSize());//文件大小
        System.out.println(file.getInputStream());//文件流


        System.out.println("图片上传"+
            file.getName()
        );
    }
}