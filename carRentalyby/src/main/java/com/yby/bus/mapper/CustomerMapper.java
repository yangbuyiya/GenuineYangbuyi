package com.yby.bus.mapper;

import com.yby.bus.domain.Customer;
import com.yby.bus.vo.CustomerVo;

import java.util.List;

public interface CustomerMapper {
    int deleteByPrimaryKey(String identity);

    int insert(Customer record);

    int insertSelective(Customer record);

    Customer selectByPrimaryKey(String identity);

    int updateByPrimaryKeySelective(Customer record);

    int updateByPrimaryKey(Customer record);
    /* 查询全部客户 */
    List<Customer> queryAllCustomer(CustomerVo customerVo);
}