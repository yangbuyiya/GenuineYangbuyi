package com.yby.bus.service;

import com.yby.bus.vo.CarVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;

/**
 * 车辆管理的服务接口
 *
 * @author 杨不易
 */
public interface CarService {

    /* 加载车辆列表返回layui表格数据格式 */
    DataGridView queryAllCar(CarVo CarVo);

    /* 添加车辆 */
    ResultObj addCar(CarVo CarVo);

    /* 修改车辆 */
    ResultObj updateCar(CarVo CarVo);

    /**
     * 根据id删除指定车辆
     *
     * @param carnumber
     * @return
     */
    ResultObj deleteCar(String carnumber);

    /**
     * 根据数组遍历 删除指定车辆 批量删除
     *
     * @param carnumbers
     * @return
     */
    ResultObj deleteBatchDelete(String[] carnumbers);

    DataGridView loadAllRentCar(CarVo carVo);
}