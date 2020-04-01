package com.yby.sys.service;

import com.yby.sys.domain.SysUser;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.vo.RoleVo;
import com.yby.sys.vo.UserVo;
import com.yby.sys.vo.UserVo;

import java.util.List;

public interface UserService {
    /**
     * 登录
     *
     * @param userVo
     * @return
     */
    SysUser login(UserVo userVo);

    /* 加载用户列表返回layui表格数据格式 */
    DataGridView queryAllUser(UserVo UserVo);

    /* 添加角色 */
    void addUser(UserVo UserVo);

    /**
     * 更新角色
     *
     * @param UserVo
     */
    void updateUser(UserVo UserVo);


    /**
     * 根据id删除指定角色
     *
     * @param userId
     * @return
     */
    void deleteUser(Integer userId);

    /**
     * 根据数组遍历 删除指定角色
     *
     * @param ids
     * @return
     */
    void deleteBatchDelete(Integer[] ids);

    /**
     * 重置密码
     *
     * @param userid
     */
    public void resetUserPwd(SysUser userid);

    /**
     * 加载用户管理的分配角色数据
     *
     * @param userid
     * @return
     */
    DataGridView queryUserRole(Integer userid);

    /**
     * 保存用户和角色的关系
     * @param userVo
     */
    void saveUserRole(UserVo userVo);

    /**
     * 根据登陆名称查询客户信息
     * @param username
     * @return
     */
    SysUser queryByLoginName(String username);

    /**
     * 根据用户id查询对应的权限
     * @param userid
     * @return
     */
    List<String> getPermissionById(Integer userid);

    List<String> queryRoleByUserIdForList(Integer userid);

}