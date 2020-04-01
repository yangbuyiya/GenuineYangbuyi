package com.yby.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.sys.domain.Menu;
import com.yby.sys.mapper.MenuMapper;
import com.yby.sys.service.MenuService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.vo.MenuVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    /**
     * 获取所有菜单信息 不分页
     * @param menuVo
     * @return
     */
    @Override
    public List<Menu> queryAllMenuForList(MenuVo menuVo) {
        return menuMapper.queryAllMenu(menuVo);
    }

    /* 后期完成权限管理之后再来改 */
    /* 完成了  直接根据 用户id 查询 他自己的菜单  */
    @Override
    public List<Menu> queryMenuByUserIdForList(MenuVo menuVo, Integer userId) {
        /* 这里就进行了 根据用户拥有的菜单进行加载 */
        return menuMapper.queryMenuByUid(menuVo.getAvailable(), userId);
    }

    /**
     * 获取所有菜单信息  分页
     * @param menuVo
     * @return
     */
    @Override
    public DataGridView queryAllMenu(MenuVo menuVo) {
        // 开启分页
        Page<Object> page = PageHelper.startPage(menuVo.getPage(), menuVo.getLimit());
        // 查询所有的菜单信息
        List<Menu> list = this.menuMapper.queryAllMenu(menuVo);
        // 返回layui格式数据
        return new DataGridView(page.getTotal(), list);
    }

    /**
     * 添加菜单
     * @param menuVo
     */
    @Override
    public void addMennu(MenuVo menuVo) {
        this.menuMapper.insertSelective(menuVo);
    }

    /**
     * 更新菜单
     * @param menuVo
     * @return
     */
    @Override
    public ResultObj updateMenu(MenuVo menuVo) {
        /**
         * 假如当前选择的菜单为
         */
        // 1.判断选中的菜单 是否为自己  违反逻辑
        if (menuVo.getId().equals(menuVo.getPid())) {
            return ResultObj.UPDATE_SELECT_ERROR; // 不能选中自己为父菜单
        }

        //2.不能设置自己的子菜单为  自己的父菜单
        // 1. 先取出当前选中的父菜单的ID
        Integer id = menuVo.getId();
        // 2. 根据选中的菜单PID 查询父菜单
        Integer pid = this.menuMapper.queryMenuByMid(menuVo.getPid());
        // 3. 判断  违反逻辑hi  当前选中的菜单id  是否 等于  自己的父菜单id
       if (pid.equals(id)) {
            // 4.返回响应
            return ResultObj.UPDATE_SELECT_CHILDREN_ERROR; // 不能选中自己的子菜单为自己的父菜单
        } else {
            // 以上都认证成功 则修改成功
            this.menuMapper.updateByPrimaryKeySelective(menuVo);
            return ResultObj.UPDATE_SUCCESS;// 修改成功
        }
    }

    /**
     * 根据父菜单id查询所有子菜单
     * @param id
     * @return
     */
    @Override
    public Integer queryMenuByPid(Integer id) {
        return this.menuMapper.queryMenuByPid(id);
    }

    /***
     * 删除指定菜单
     * @param menuVo
     * @return
     */
    @Override
    public ResultObj deleteMenu(MenuVo menuVo) {
        try {
            // 1.删除菜单表当中的数据
            this.menuMapper.deleteByPrimaryKey(menuVo.getId());
            // 2.删除菜单表与角色表关联的数据
            this.menuMapper.deleteRoleMenuByMid(menuVo.getId());
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }
    }

    /**
     * 批量删除菜单
     * @param ids
     * @return
     */
    @Override
    public ResultObj batchDelete(Integer[] ids) {
        try {
            for (Integer id : ids) {
                /* 打破关系 */
                this.menuMapper.deleteRoleMenuByMid(id);
                /* 删除菜单 */
                this.menuMapper.deleteByPrimaryKey(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.DELETE_ERROR;
        }
    }
}