package com.yby.sys.mapper;

import com.yby.sys.domain.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer userid);

    List<SysUser> selectAll();

    int updateByPrimaryKeySelective (SysUser sysUser);

    int updateByPrimaryKey(SysUser record);

    /**
     * 登录
     * @param sysUser
     * @return
     */
    SysUser login(SysUser sysUser);

    /**
     * 获取用户
     * @param sysUser
     * @return
     */
    List<SysUser> queryAllUser(SysUser sysUser);

    /**
     * 保存用户和角色的更关系
     * @param userid
     * @param id
     */
    void insertUserRole(@Param("uid") Integer userid, @Param("rid") Integer id);

    SysUser queryByLoginName(String username);

    List<String> getPermissionById(Integer userid);

    List<String> queryRoleByUserIdForList(Integer userid);
}