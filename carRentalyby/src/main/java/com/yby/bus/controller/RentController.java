package com.yby.bus.controller;

import com.yby.bus.domain.Customer;
import com.yby.bus.service.CustomerService;
import com.yby.bus.service.RentService;
import com.yby.bus.vo.RentVo;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.SysUser;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.RandomUtils;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/***
 * 汽车出租和出租单管理的控制器
 * 这一层是返回数据到前端的
 */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("rent")
public class RentController {

    /* 调用出租业务 */
    @Autowired
    private RentService rentService;

    /* 调用客户业务 */
    @Autowired
    private CustomerService customerService;

    /**
     * 根据身份证客户查询相关数据
     *
     * @param rentVo
     * @return
     */
    @PostMapping("checkCustomerExist")
    public ResultObj checkCustomerExist(RentVo rentVo) {
        Customer customer = customerService.queryCustomerByIdentity(rentVo.getIdentity());
        if (null != customer) {
            return ResultObj.STATUS_TRUE;
        }
        return ResultObj.STATUS_FALSE;
    }


    /* 2020 3月1日 2点42分钟 重新开始项目 */

    /**
     * 初始化添加出租单列表
     *
     * @param rentVo
     * @return
     */
    @GetMapping("initRentFrom")
    public RentVo initRentFrom(RentVo rentVo) {
        // 生成出租单号
        rentVo.setRentid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_CZ));
        // 设置起租时间
        rentVo.setBegindate(new Date());
        // 获取当前用户
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        // 设置操作员
        rentVo.setOpername(user.getRealname());
        return rentVo;
    }

    /**
     * 保存出租单
     */
    @PostMapping("saveRent")
    public ResultObj saveRent(RentVo rentVo) {
        try {
            // 设置订单创建时间
            rentVo.setCreatetime(new Date());
            // 设置归还状态
            rentVo.setRentflag(SysConstast.RENT_CAR_FALSE);
            // 保存
            this.rentService.addRent(rentVo);
            return ResultObj.ADD_SUCCESS;
        }catch (Exception e){
            return ResultObj.ADD_ERROR;
        }
    }

    /*-------------------------------出租单管理-----------------------------------*/

    /**
     * 初始化出租单
     * @param rentVo
     * @return 数据
     */
    @PostMapping("loadAllRent")
    public DataGridView loadAllRent(RentVo rentVo) {
       return this.rentService.queryAllRent(rentVo);
    }


    /**
     * 保更新出租单
     */
    @PostMapping("updateRent")
    public ResultObj updateRent(RentVo rentVo) {
        try {
            // 更新
            this.rentService.updateRent(rentVo);
            return ResultObj.UPDATE_SUCCESS;
        }catch (Exception e){
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除出租单
     */
    @PostMapping("deleteRent")
    public ResultObj deleteRent(RentVo rentVo) {
        try {
            this.rentService.deleteRent(rentVo.getRentid());
            return ResultObj.DELETE_SUCCESS;
        }catch (Exception e){
            return ResultObj.DELETE_ERROR;
        }
    }

}