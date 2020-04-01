package com.yby.bus.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 业务管理的路由控制器
 * 这里是进行跳转映射到前端页面
 * @author TeouBle
 */
@Controller
@RequestMapping("bus")
public class BusController {

    /* **********************************客户管理********************************************** */

    /**
     * 跳转到客户管理
     * @return 跳转页面
     */
    @RequestMapping("toCustomerManager")
    public String toCustomerManager() {
        return "business/customer/customerManager";
    }

    /* **********************************车辆管理********************************************** */

    /**
     * 跳转到车辆管理
     * @return 跳转页面
     */
    @RequestMapping("toCarManager")
    public String toCarManager() {
        return "business/car/carManager";
    }

    /* **********************************出租管理********************************************** */

    /**
     * 跳转到出租管理
     * @return 跳转页面
     */
    @RequestMapping("toRentCarManager")
    public String toRentCarManager() {
        return "business/rent/rentCarManager";
    }

    /* **********************************出租单管理********************************************** */

    /**
     * 跳转到出租单管理
     * @return 跳转页面
     */
    @RequestMapping("toRentManager")
    public String toRentManager() {
        return "business/rent/rentManager";
    }

    /* **********************************汽车入库管理********************************************** */

    /**
     * 跳转到汽车入库管理
     * @return 跳转页面
     */
    @RequestMapping("toCheckCarManager")
    public String toCheckCarManager() {
        return "business/check/checkCarManager";
    }


    /* **********************************检查管理********************************************** */

    /**
     * 跳转到检查管理
     * @return 跳转页面
     */
    @RequestMapping("toCheckManager")
    public String toCheckManager() {
        return "business/check/checkManager";
    }



}