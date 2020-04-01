package com.yby.sys.utils;


import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.Menu;

import java.util.ArrayList;
import java.util.List;

// 把简单的树 转化  有层级关系的
// children  子节点
public class TreeNodeBuilder {

    /**
     * 重构层级菜单
     *
     * @param nodes  全部菜单  根据用户查询出来的全部菜单
     * @param topPid 父菜单id
     * @return
     */
    public static List<TreeNode> builder(List<TreeNode> nodes, Integer topPid) { //   6
        List<TreeNode> treeNodes = new ArrayList<>();
        // 取出每一条数据
        for (TreeNode n1 : nodes) {
            // 判断 当前的pid(父节点) 是否等于 传入进来的 id
            if (n1.getPid() == topPid) {
                // 等于则添加到集合当中   这里就是判断是否为 父节点
                treeNodes.add(n1);
            }
            // 循环子节点
            for (TreeNode n2 : nodes) {
                // 判断子节点 是否等于  父节点  的id
                if (n2.getPid() == n1.getId()) {
                    // 等于则 该子节点是  n1的子节点
                    n1.getChildren().add(n2);

                    // 递归获取子节点
                    builder(treeNodes, n2.getPid());
                }
            }
        }
        return treeNodes;
    }


    /**
     * 封装菜单数据
     * @param list 菜单
     * @return
     */
    public static List<TreeNode> treeNodeDataGridView(List<Menu> list) {
        List<TreeNode> nodes = new ArrayList<>();
        // 循环菜单数据
        for (Menu menu : list) {
            Integer id = menu.getId();
            Integer pid = menu.getPid();
            String title = menu.getTitle();
            String icon = menu.getIcon();
            String href = menu.getHref();
            Boolean spread = menu.getSpread() == SysConstast.SPREAD_TRUE ? true : false;
            String target = menu.getTarget();
            // 添加到treenode集合当中  显示菜单需要的数据
            nodes.add(new TreeNode(id, pid, title, icon, href, spread, target));
        }
        // 返回需要的数据
        return nodes;
    }


    /**
     * 重构角色分配菜单数据
     * @param roleid  角色id
     * @param queryAllMenu  查询出来的全部菜单
     * @param roleMenu  根据角色id 查询出来的菜单
     * @return
     */
    public static DataGridView treeNodeDataRoleMenu(Integer roleid, List<Menu> queryAllMenu, List<Menu> roleMenu) {
        List<TreeNode> data = new ArrayList<>();
        // 3.如果当前角色为超级管理员  全部进行勾选菜单
        if (roleid == 1) {
            if (queryAllMenu != null) {
                for (Menu menu1 : queryAllMenu) {
                    // 判断是否展开
                    Boolean spread = menu1.getSpread() == SysConstast.SPREAD_TRUE ? true : false;
                    // 重构数据
                    data.add(new TreeNode(menu1.getId(), menu1.getPid(), menu1.getTitle(), spread, SysConstast.CODE_ONE + ""));
                }
            }
        } else {
            // 4.普通管理员   就根据自己拥有的菜单 进行勾选
            if (queryAllMenu != null) {
                for (Menu menu1 : queryAllMenu) {
                    // checkArr  layui复选框的重要约束
                    String checked = SysConstast.CODE_ZERO + "";
                    for (Menu menu2 : roleMenu) {
                        if (menu1.getId() == menu2.getId()) {
                            checked = SysConstast.CODE_ONE + "";
                            break;
                        }
                    }
                    // 判断是否展开
                    Boolean spread = menu1.getSpread() == SysConstast.SPREAD_TRUE ? true : false;
                    // 重构数据
                    data.add(new TreeNode(menu1.getId(), menu1.getPid(), menu1.getTitle(), spread, checked));
                }
            }
        }
        // 返回重构数据
        return new DataGridView(data);
    }
}

