package com.yby.stat.mapper;

import com.yby.stat.domain.BaseEntity;

import java.util.List;

/**
 * @author TeouBle
 */
public interface StatMapper {

    /***
     * 查询客户地区数据
     * @return
     */
    public List<BaseEntity> queryCustomerAreaStat();

    /**
     * 加载业务员年度业务统计数据
     * @param year
     * @return
     */
    List<BaseEntity> queryOpernameYearGradeStat(String year);

    /**
     * 公司 年度销售额统计
     * @param year
     * @return
     */
    List<Double> queryCompanyYearGradeStat(String year);
}