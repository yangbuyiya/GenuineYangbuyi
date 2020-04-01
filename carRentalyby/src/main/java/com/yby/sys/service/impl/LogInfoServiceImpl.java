package com.yby.sys.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.sys.domain.LogInfo;
import com.yby.sys.mapper.LogInfoMapper;
import com.yby.sys.service.LogInfoService;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.vo.LogInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/* 我们使用了aop切面 实现了事务管理 所有不需要 @Transactional  声明 */
@Service
public class LogInfoServiceImpl implements LogInfoService {

    // loginfo dao层
    @Autowired
    private LogInfoMapper logInfoMapper;

    /**
     * 查询日志
     *
     * @param logInfoVo
     * @return
     */
    @Override
    public DataGridView queryAllLogInfo(LogInfoVo logInfoVo) {
        Page<Object> page = PageHelper.startPage(logInfoVo.getPage(), logInfoVo.getLimit());
        List<LogInfo> data = this.logInfoMapper.queryAllLogInfo(logInfoVo);
        return new DataGridView(page.getTotal(), data);
    }

    /**
     * 添加日志
     *
     * @param logInfoVo
     */
    @Override
    public void addLogInfo(LogInfoVo logInfoVo) {
        this.logInfoMapper.insertSelective(logInfoVo);
    }

    /**
     * 删除单个日志
     *
     * @param logInfoid
     */
    @Override
    public void deleteLogInfo(Integer logInfoid) {
        this.logInfoMapper.deleteByPrimaryKey(logInfoid);
    }

    /**
     * 批量删除日志
     *
     * @param ids
     */
    @Override
    public void deleteBatchDelete(Integer[] ids) {
        for (Integer id : ids) {
            this.deleteLogInfo(id);
        }
    }
}