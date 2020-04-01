package com.yby.bus.controller;

import com.yby.bus.service.CarService;
import com.yby.bus.vo.CarVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
 * 车辆管理的控制器  这一层是返回数据到前端的
 * */
/* controller  和 responseBody  一起使用 */
@RestController
@RequestMapping("car")
public class CarController {

    @Autowired
    private CarService carService;

    /* 加载车辆列表返回layui表格数据格式 */
    @RequestMapping("loadAllCar")
    public DataGridView loadAllCar(CarVo CarVo, String re) {
        System.out.println(re);
        if (re != null && "rentCar".equals(re)) {
            return this.carService.loadAllRentCar(CarVo);
        }
        return this.carService.queryAllCar(CarVo);
    }


    /**
     * 添加车辆
     *
     * @param carVo
     */
    @RequestMapping("addCar")
    public ResultObj addCar(CarVo carVo) {
        return this.carService.addCar(carVo);
    }

    /**
     * 修改车辆
     *
     * @param carVo
     */
    @RequestMapping("updateCar")
    public ResultObj updateCar(CarVo carVo) {
        return this.carService.updateCar(carVo);
    }

    /**
     * 删除车辆
     *
     * @param CarVo
     * @return
     */
    @RequestMapping("deleteCar")
    public ResultObj deleteCar(CarVo CarVo) {
        return this.carService.deleteCar(CarVo.getCarnumber());
    }

    /**
     * 批量删除车辆
     *
     * @param CarVo
     * @return
     */
    @RequestMapping("deleteBatchDelete")
    public ResultObj deleteBatchDelete(CarVo CarVo) {
        return this.carService.deleteBatchDelete(CarVo.getCarnumbers());
    }

}