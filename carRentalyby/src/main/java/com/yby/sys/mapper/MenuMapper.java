package com.yby.sys.mapper;

import com.yby.sys.domain.Menu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MenuMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    /* 查询所有菜单信息 */
    List<Menu> queryAllMenu(Menu menu);

    /* 根据pid查询菜单数量 */
    Integer queryMenuByPid(@Param("pid") Integer pid);

    /*  根据菜单id删除sys_role_menu里面的数据 */
    void deleteRoleMenuByMid(@Param("mid") Integer mid);

    /* 根据角色id 查询当前角色用有的菜单  只查询可用的 */
    List<Menu> queryMenuByRoleId(@Param("available") Integer available, @Param("rid") Integer rid);

    /* 根据用户id查询菜单 */
    List<Menu> queryMenuByUid(@Param("available") Integer available, @Param("uid") Integer userId);

    /* 根据菜单id  查询出父菜单id */
    Integer queryMenuByMid(@Param("id") Integer id);
}