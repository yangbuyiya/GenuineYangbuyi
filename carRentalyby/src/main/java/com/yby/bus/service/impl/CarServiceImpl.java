package com.yby.bus.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.bus.domain.Car;
import com.yby.bus.mapper.CarMapper;
import com.yby.bus.service.CarService;
import com.yby.bus.vo.CarVo;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.utils.AppFileUtils;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/* 我们使用了aop切面 实现了事务管理 所有不需要 @Transactional  声明 */
@Service
public class CarServiceImpl implements CarService {

    // Car dao层
    @Autowired
    private CarMapper carMapper;

    /**
     * 查询车辆
     *
     * @param carVo
     * @return
     */
    @Override
    public DataGridView queryAllCar(CarVo carVo) {
        Page<Object> page = PageHelper.startPage(carVo.getPage(), carVo.getLimit());
        List<Car> data = this.carMapper.queryAllCar(carVo);
        return new DataGridView(page.getTotal(), data);
    }

    /**
     * 添加车辆
     *
     * @param carVo
     */
    @Override
    public ResultObj addCar(CarVo carVo) {
        try {
            carVo.setCreatetime(new Date());
            if (!carVo.getCarimg().equals(SysConstast.DEFAULT_CAR_IMG)) {
                // 去掉图片标记
                String s = AppFileUtils.updateFileName(carVo.getCarimg(), SysConstast.FILE_UPLOAD_TEMP);
                carVo.setCarimg(s);
            }
            this.carMapper.insertSelective(carVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            return ResultObj.ADD_ERROR;
        }
    }

    /**
     * 修改车辆
     *
     * @param carVo
     */
    @Override
    public ResultObj updateCar(CarVo carVo) {
        try {
            carVo.setCreatetime(new Date());
            /*if(!carVo.getCarimg().equals(SysConstast.DEFAULT_CAR_IMG)){
                // 去掉图片标记
                String s = AppFileUtils.updateFileName(carVo.getCarimg(), SysConstast.FILE_UPLOAD_TEMP);
                carVo.setCarimg(s);
            }*/
            String carimg = carVo.getCarimg();
            /* 如果结尾包含_temp */
            if (carimg.endsWith(SysConstast.FILE_UPLOAD_TEMP)) {
                // 去掉图片标记
                String s = AppFileUtils.updateFileName(carVo.getCarimg(), SysConstast.FILE_UPLOAD_TEMP);

                carVo.setCarimg(s);
                // 把原来的删除
                Car car = this.carMapper.selectByPrimaryKey(carVo.getCarnumber());
                // 根据路径删除本地图片
                AppFileUtils.removeFileByPath(car.getCarimg());
            }

            this.carMapper.updateByPrimaryKeySelective(carVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.UPDATE_ERROR;
        }
    }

    /**
     * 删除单个车辆
     *
     * @param carnumber
     */
    @Override
    public ResultObj deleteCar(String carnumber) {
        try {
            // 先删除图片
            // 把原来的删除
            Car car = this.carMapper.selectByPrimaryKey(carnumber);

            // 如果不是默认图片那就删除
            if(!car.getCarimg().equals(SysConstast.DEFAULT_CAR_IMG)){
                // 根据路径删除本地图片
                AppFileUtils.removeFileByPath(car.getCarimg());
            }

            this.carMapper.deleteByPrimaryKey(carnumber);
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }

    }

    /**
     * 批量删除车辆
     *
     * @param carnumbers
     */
    @Override
    public ResultObj deleteBatchDelete(String[] carnumbers) {
        try {
            for (String carnumber : carnumbers) {
                this.deleteCar(carnumber);
            }
            return ResultObj.DELETE_SUCCESS;
        } catch (Exception e) {
            return ResultObj.DELETE_ERROR;
        }


    }

    @Override
    public DataGridView loadAllRentCar(CarVo carVo) {
        Page<Object> page = PageHelper.startPage(carVo.getPage(), carVo.getLimit());
        List<Car> data = this.carMapper.loadAllRentCar(carVo);

        return new DataGridView(page.getTotal(), data);
    }

}