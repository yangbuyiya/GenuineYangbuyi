package com.yby.bus.service;

import com.yby.bus.domain.Customer;
import com.yby.bus.vo.CustomerVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;

import java.util.List;

/**
 * 客户管理的服务接口
 *
 * @author 杨不易
 */
public interface CustomerService {

    /* 加载客户列表返回layui表格数据格式 */
    DataGridView queryAllCustomer(CustomerVo CustomerVo);

    /* 添加客户 */
    ResultObj addCustomer(CustomerVo CustomerVo);

    /* 修改客户 */
    ResultObj updateCustomer(CustomerVo CustomerVo);

    /**
     * 根据id删除指定客户
     *
     * @param CustomerId
     * @return
     */
    ResultObj deleteCustomer(String CustomerId);

    /**
     * 根据数组遍历 删除指定客户 批量删除
     *
     * @param ids
     * @return
     */
    ResultObj deleteBatchDelete(String[] ids);

    /**
     * 根据身份证号查询相关信息
     * @param identity
     * @return
     */
    Customer queryCustomerByIdentity(String identity);

    /**
     * 查询客户所有信息
     * @param customerVo
     * @return
     */
    List<Customer> queryAllCustomerForList(CustomerVo customerVo);
}