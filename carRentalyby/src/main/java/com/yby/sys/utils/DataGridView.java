package com.yby.sys.utils;

import lombok.Data;

/**
 * 封装layui数据表格的数据对象
 * @author TeouBle
 */
@Data
public class DataGridView {
    /**
     * 返回状态码
     */
    private Integer code=0;
    /**
     * 描述
     */
    private String msg="";
    /**
     * 总记录数
     */
    private Long count;
    /**
     * 返回的数据
     */
    private Object data;

    /**
     * 自定义一下空构造器 因为要使用有参构造
     */
    public DataGridView() {
    }

    /**
     * 返回layui格式数据
     * @param data
     */
    public DataGridView(Object data) {
        super();
        this.data = data;
    }

    /***
     * 返回layui格式数据
     * @param count 总条数
     * @param data  数据
     */
    public DataGridView(Long count, Object data) {
        super();
        this.count = count;
        this.data = data;
    }

}