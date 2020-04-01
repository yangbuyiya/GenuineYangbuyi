package com.yby.bus.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.bus.domain.Customer;
import com.yby.bus.mapper.CustomerMapper;
import com.yby.bus.service.CustomerService;

import com.yby.bus.vo.CustomerVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/* 我们使用了aop切面 实现了事务管理 所有不需要 @Transactional  声明 */
@Service
public class CustomerServiceImpl implements CustomerService {

    // Customer dao层
    @Autowired
    private CustomerMapper customerMapper;

    /**
     * 查询客户
     *
     * @param customerVo
     * @return
     */
    @Override
    public DataGridView queryAllCustomer(CustomerVo customerVo) {
        Page<Object> page = PageHelper.startPage(customerVo.getPage(), customerVo.getLimit());
        List<Customer> data = this.customerMapper.queryAllCustomer(customerVo);
        return new DataGridView(page.getTotal(), data);
    }

    /**
     * 添加客户
     *
     * @param customerVo
     */
    @Override
    public ResultObj addCustomer(CustomerVo customerVo) {
        try {
            customerVo.setCreatetime(new Date());
            this.customerMapper.insertSelective(customerVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改客户
     *
     * @param customerVo
     */
    @Override
    public ResultObj updateCustomer(CustomerVo customerVo) {
        try {
            customerVo.setCreatetime(new Date());
            this.customerMapper.updateByPrimaryKeySelective(customerVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单个客户
     *
     * @param customerid
     */
    @Override
    public ResultObj deleteCustomer(String customerid) {
        try {
            this.customerMapper.deleteByPrimaryKey(customerid);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 批量删除客户
     *
     * @param ids
     */
    @Override
    public ResultObj deleteBatchDelete(String[] ids) {
        try {
            for (String id : ids) {
                this.deleteCustomer(id);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }


    }

    @Override
    public Customer queryCustomerByIdentity(String identity) {
        return this.customerMapper.selectByPrimaryKey(identity);
    }

    /**
     * 查询客户所有信息 导出的
     *
     * @param customerVo
     * @return
     */
    @Override
    public List<Customer> queryAllCustomerForList(CustomerVo customerVo) {
        return this.customerMapper.queryAllCustomer(customerVo);
    }

}