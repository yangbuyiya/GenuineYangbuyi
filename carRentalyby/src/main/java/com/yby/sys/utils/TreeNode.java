package com.yby.sys.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
/* 菜单数据 */

/**
 * 这里是进行重构菜单数据类
 */
@ToString
public class TreeNode {
    /* 编号 */
    private Integer id;
    /* 父节点 */
    @JsonProperty("parentId")
    private Integer pid;
    /* 名称 */
    private String title;
    /* 图标 */
    private String icon;
    /* 地址 */
    private String href;
    /* 是否展开 */
    private Boolean spread;
    /* 打开页面状态 */
    private String target;
    /* 子节点 层级关系 */
    private List<TreeNode> children = new ArrayList<>();
    //复选树的必要属性
    private String checkArr = "0";//选中就是1

    /**
     * 首页左边导航树的构造器
     *
     * @param id
     * @param pid
     * @param title
     * @param icon
     * @param href
     * @param spread
     * @param target
     */
    public TreeNode(Integer id, Integer pid, String title, String icon, String href, Boolean spread, String target) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.icon = icon;
        this.href = href;
        this.spread = spread;
        this.target = target;
    }

    /**
     * 给dtree的复选树使用
     *
     * @param id
     * @param pid
     * @param title
     * @param spread
     * @param checkArr
     */
    public TreeNode(Integer id, Integer pid, String title, Boolean spread, String checkArr) {
        this.id = id;
        this.pid = pid;
        this.title = title;
        this.spread = spread;
        this.checkArr = checkArr;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public Boolean getSpread() {
        return spread;
    }

    public void setSpread(Boolean spread) {
        this.spread = spread;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public String getCheckArr() {
        return checkArr;
    }

    public void setCheckArr(String checkArr) {
        this.checkArr = checkArr;
    }
}
