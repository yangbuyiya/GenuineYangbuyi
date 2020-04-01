package com.yby.sys.controller;

import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Menu;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.MenuService;
import com.yby.sys.utils.*;
import com.yby.sys.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * 菜单管理的控制器
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("menu")
public class MenuController {

    /* 菜单业务类 */
    @Autowired
    private MenuService menuService;

    /**
     * 入口菜单
     *
     * @param menuVo
     * @return
     */
    @RequestMapping("loadIndexLeftMenuJson")
    public List<TreeNode> loadIndexLeftMenuJson(MenuVo menuVo) {
        // 得到当前用户登录的对象
        SysUser sysUser = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        // 获取菜单
        List<Menu> list = null;
        // 只查询可用的
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
        // 判断是否为超级管理员
        if (sysUser.getType() == SysConstast.AVAILABLE_TRUE) {
            // 如果是超级管理员  则拥有所有菜单权限
            list = this.menuService.queryAllMenuForList(menuVo);
        } else {
            // 不是   则根据用户id 查询当前用户的菜单
            list = this.menuService.queryMenuByUserIdForList(menuVo, sysUser.getUserid());
        }
        System.out.println("入口菜单:"+list);
        // 把查询到的所有菜单 放到 nodes
        List<TreeNode> treeNodes = TreeNodeBuilder.treeNodeDataGridView(list);
        /* 处理层级关系  layui风格  */
        return TreeNodeBuilder.builder(treeNodes, 1);

    }


    /* 加载菜单管理左边的菜单树 */
    @RequestMapping("loadMenuManagerLeftTreeJson")
    public DataGridView loadMenuManagerLeftTreeJson(MenuVo menuVo) {
        // 只查询可用的
        menuVo.setAvailable(SysConstast.AVAILABLE_TRUE);
        // 查询菜单
        List<Menu> list = this.menuService.queryAllMenuForList(menuVo);
        // 进行封装菜单 返回给layui
        List<TreeNode> treeNodes = TreeNodeBuilder.treeNodeDataGridView(list);
        // 返回菜单   list风格  不是标准风格
        return new DataGridView(treeNodes);
    }


    /* 加载菜单列表返回layui表格数据格式 */
    @RequestMapping("loadAllMenu")
    public DataGridView loadAllMenu(MenuVo menuVo) {
        return this.menuService.queryAllMenu(menuVo);
    }

    /* 添加菜单 */
    @RequestMapping("addMenu")
    public ResultObj addMenu(MenuVo menuVo) {
        try {
            this.menuService.addMennu(menuVo);
            return ResultObj.ADD_SUCCESS;// 添加成功
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;// 添加失败
        }
    }

    /* 修改菜单 */
    @RequestMapping("updateMenu")
    public ResultObj updateMenu(MenuVo menuVo) {
        try {
            return this.menuService.updateMenu(menuVo);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;// 修改失败
        }
    }

    /**
     * 根据id判断当前菜单有没有子节点
     * 有 返回code>=0
     * 没有 返回code<0
     *
     * @param menuVo
     * @return
     */
    @RequestMapping("checkMenuHasChildren")
    public ResultObj checkMenuHasChildren(MenuVo menuVo) {
        //根据pid查询菜单数量
        Integer count = this.menuService.queryMenuByPid(menuVo.getId());
        if (count > 0) {
            return ResultObj.STATUS_TRUE;
        }
        return ResultObj.STATUS_FALSE;
    }

    /**
     * 删除菜单
     *
     * @param menuVo
     * @return
     */
    @RequestMapping("deleteMenu")
    public ResultObj deleteMenu(MenuVo menuVo) {
        return this.menuService.deleteMenu(menuVo);
    }

    /**
     * 批量删除菜单/
     *
     * @param ids
     * @return
     */
    @RequestMapping("batchDelete")
    public ResultObj batchDelete(@RequestParam("ids[]") Integer[] ids) {
        return this.menuService.batchDelete(ids);
    }

}