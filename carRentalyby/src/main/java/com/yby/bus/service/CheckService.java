package com.yby.bus.service;

import com.yby.bus.domain.Check;
import com.yby.bus.vo.CheckVo;
import com.yby.sys.utils.DataGridView;

import java.util.Map;

/**
 * 检查管理的服务器接口
 *
 * @author 杨不易
 */
public interface CheckService {
    /**
     * 根据出租单号查询出租单信息
     *
     * @param rentid
     * @return Map<String   ,       Object>
     */
    Map<String, Object> initCheckFormData(String rentid);

    /**
     * '
     * 保存检查单信息
     *
     * @param checkVo
     */
    void addCheck(CheckVo checkVo);

    /**
     * 查询检查单
     *
     * @param checkVo
     * @return 返回查询出的数据
     */
    DataGridView queryAllCheck(CheckVo checkVo);

    /**
     * 更新检查单
     *
     * @param checkVo
     * @return 返回响应
     */
    void updateCheck(CheckVo checkVo);
}