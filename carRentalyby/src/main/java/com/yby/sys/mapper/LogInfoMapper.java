package com.yby.sys.mapper;

import com.yby.sys.domain.LogInfo;
import com.yby.sys.vo.LogInfoVo;

import java.util.List;

public interface LogInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(LogInfo record);

    int insertSelective(LogInfo record);

    LogInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(LogInfo record);

    int updateByPrimaryKey(LogInfo record);

    /* 查询全部日志 */
    public List<LogInfo> queryAllLogInfo(LogInfoVo info);

}