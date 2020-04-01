package com.yby.sys.mapper;

import com.yby.sys.domain.Permission;
import com.yby.sys.utils.DataXSelect;
import com.yby.sys.vo.PermissionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Long pid);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long pid);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);


    List<String> queryPermissionByUserId(Integer userid);

    List<Permission> queryAllPermission(PermissionVo permissionVo);

    void addPermission(PermissionVo permissionVo);

    void addPermissionRole(@Param("id") Integer id, @Param("pid") Long pid);

    Permission queryByPresource(String presource);

    List<DataXSelect> permissionPname();

    List<DataXSelect> permissionPresource();

    void deletePermissionRole(@Param("integer") Integer integer, @Param("aLong") Long aLong);

    List<DataXSelect> queryPermissionByUserIdandpname(Integer userid);
}