package com.yby.bus.controller;

import com.yby.bus.service.CustomerService;
import com.yby.bus.vo.CustomerVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 公告管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 以json格式返回数据 */
@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    /* 加载公告列表返回layui表格数据格式 */
    @RequestMapping("loadAllCustomer")
    public DataGridView loadAllCustomer(CustomerVo CustomerVo) {
        return this.customerService.queryAllCustomer(CustomerVo);
    }


    /**
     * 添加公告
     *
     * @param customerVo
     */
    @RequestMapping("addCustomer")
    public ResultObj addCustomer(CustomerVo customerVo) {
        return this.customerService.addCustomer(customerVo);
    }

    /**
     * 修改广告
     *
     * @param customerVo
     */
    @RequestMapping("updateCustomer")
    public ResultObj updateCustomer(CustomerVo customerVo) {
        return this.customerService.updateCustomer(customerVo);
    }

    /**
     * 删除公告
     *
     * @param CustomerVo
     * @return
     */
    @RequestMapping("deleteCustomer")
    public ResultObj deleteCustomer(CustomerVo CustomerVo) {
        return this.customerService.deleteCustomer(CustomerVo.getIdentity());
    }

    /**
     * 批量删除公告
     *
     * @param CustomerVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    public ResultObj deleteBatchDelete(CustomerVo CustomerVo) {
        return this.customerService.deleteBatchDelete(CustomerVo.getIds());
    }




}