package com.yby.stat.service;


import com.yby.stat.domain.BaseEntity;

import java.util.List;

/**
 * 统计分析业务
 *
 * @author TeouBle
 */
public interface StatService {

    /**
     * 查询客户地区数据
     * @return
     */
    List<BaseEntity> loadCustomerAreaStatJson();

    /**
     * 加载业务员年度业务统计数据
     * @param year
     * @return
     */
    List<BaseEntity> loadOpernameYearGradeStatList(String year);
    /**
     * 公司 年度销售额统计
     * @param year
     * @return
     */
    List<Double> loadCompanyYearGradeStatList(String year);
}