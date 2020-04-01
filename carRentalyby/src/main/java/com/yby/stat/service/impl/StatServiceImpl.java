package com.yby.stat.service.impl;

import com.yby.stat.domain.BaseEntity;
import com.yby.stat.mapper.StatMapper;
import com.yby.stat.service.StatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatServiceImpl implements StatService {

    @Autowired
    private StatMapper statMapper;

    /**
     * 查询客户地区数据
     *
     * @return
     */
    @Override
    public List<BaseEntity> loadCustomerAreaStatJson() {
        return this.statMapper.queryCustomerAreaStat();
    }

    /**
     * 加载业务员年度业务统计数据
     * @param year
     * @return
     */
    @Override
    public List<BaseEntity> loadOpernameYearGradeStatList(String year) {
        return statMapper.queryOpernameYearGradeStat(year);
    }
    /**
     * 公司 年度销售额统计
     * @param year
     * @return
     */
    @Override
    public List<Double> loadCompanyYearGradeStatList(String year) {
        return statMapper.queryCompanyYearGradeStat(year);
    }
}