package com.yby.sys.service;

import com.yby.sys.domain.LogInfo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.vo.LogInfoVo;

import java.util.List;

/**
 * 日志管理的服务接口
 *
 * @author 杨不易
 */
public interface LogInfoService {

    /* 加载日志列表返回layui表格数据格式 */
    DataGridView queryAllLogInfo(LogInfoVo LogInfoVo);

    /* 添加日志 */
    void addLogInfo(LogInfoVo LogInfoVo);

    /**
     * 根据id删除指定日志
     *
     * @param LogInfoId
     * @return
     */
    void deleteLogInfo(Integer LogInfoId);

    /**
     * 根据数组遍历 删除指定日志 批量删除
     *
     * @param ids
     * @return
     */
    void deleteBatchDelete(Integer[] ids);


}