package com.yby.sys.service;

import com.yby.sys.domain.Menu;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.MenuVo;

import java.util.List;

/*
 * 菜单管理服务接口
 * */
public interface MenuService {
    /* 查询所有菜单
     * 返回：List<menu> */
    public List<Menu> queryAllMenuForList(MenuVo menuVo);

    /*
     * 问题
     * 不同的用户看到的菜单是不一样的
     * 根据用户Id查询用户的可用菜单
     * */
    public List<Menu> queryMenuByUserIdForList(MenuVo menuVo, Integer userId);

    /* 加载菜单列表返回layui表格数据格式 */
    DataGridView queryAllMenu(MenuVo menuVo);
    /* 添加菜单 */
    void addMennu(MenuVo menuVo);

    /**
     * 更新菜单
     * @param menuVo
     */
    ResultObj updateMenu(MenuVo menuVo);

    /**
     * 根据pid查询菜单数量
     * @param id
     * @return
     */
    Integer queryMenuByPid(Integer id);

    /**
     * 根据id删除指定菜单
     * @param menuVo
     * @return
     */
    ResultObj deleteMenu(MenuVo menuVo);

    /**
     * 根据数组遍历 删除指定菜单
     * @param ids
     * @return
     */
    ResultObj batchDelete(Integer[] ids);
}