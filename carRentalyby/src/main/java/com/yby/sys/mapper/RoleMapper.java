package com.yby.sys.mapper;

import com.yby.sys.domain.Role;
import com.yby.sys.utils.DataXSelect;
import com.yby.sys.vo.PermissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleid);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleid);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    List<Role> queryAllRole(Role role);

    void deleteRoleMenuByRid(Integer roleId);

    void deleteRoleUserByUid(@Param("uid") Integer roleId);

    void saveRoleMenu(@Param("rid") Integer rid, @Param("mid") Integer mid);

    List<Role> queryRoleByUid(@Param("availableTrue") Integer availableTrue, @Param("userid") Integer userid);

    List<DataXSelect> queryPermissionRole();

    List<DataXSelect> queryPermissionRoleUid(PermissionVo permissionVo);

    List<DataXSelect> queryRoleByUids(Integer userid);
}