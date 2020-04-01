package com.yby.bus.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.bus.domain.Car;
import com.yby.bus.domain.Rent;
import com.yby.bus.mapper.CarMapper;
import com.yby.bus.mapper.RentMapper;
import com.yby.bus.service.RentService;
import com.yby.bus.vo.RentVo;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.utils.DataGridView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author TeouBle
 */
@Service
public class RentServiceImpl implements RentService {

    @Autowired
    private RentMapper rentMapper;

    @Autowired
    private CarMapper carMapper;

    /*-----------------------------汽车车出租--------------------------------*/

    /**
     * 添加出租单
     * @param rentVo
     */
    @Override
    public void addRent(RentVo rentVo) {
        // 添加出租订单
        this.rentMapper.insertSelective(rentVo);
        // 更改该车辆的出租状态
        Car car = new Car();
        // 设置车辆名称
        car.setCarnumber(rentVo.getCarnumber());
        // 设置车辆为已出租状态
        car.setIsrenting(SysConstast.RENT_CAR_TRUE);
        // 更新车辆
        carMapper.updateByPrimaryKeySelective(car);
    }

    /**
     * 查询出租信息
     * @param rentVo
     * @return
     */
    @Override
    public DataGridView queryAllRent(RentVo rentVo) {
        // 分页插件
        Page<Object> page = PageHelper.startPage(rentVo.getPage(),rentVo.getLimit());
        List<Rent> data = this.rentMapper.queryAllRent(rentVo);
        // 返回总记录数 和数据
        return new DataGridView(page.getTotal(),data);

    }

    /*-----------------------------出租单管理--------------------------------*/

    /**
     * 更新出租
     * @param rentVo
     */
    @Override
    public void updateRent(RentVo rentVo) {
        this.rentMapper.updateByPrimaryKeySelective(rentVo);
    }

    @Override
    public void deleteRent(String rentid) {
        /*
        * 1.更改汽车状态，将已出租的状态转换成未出租的状态
        * 2.删除出租单
        * */

        //更改汽车状态，将已出租的状态转换成未出租的状态
        Rent rent = this.rentMapper.selectByPrimaryKey(rentid);

        Car car = new Car();
        car.setCarnumber(rent.getCarnumber());
        //转换成未出租的状态
        car.setIsrenting(SysConstast.RENT_CAR_FALSE);
        // 进行更改出租状态
        carMapper.updateByPrimaryKeySelective(car);
        // 删除出租单
        this.rentMapper.deleteByPrimaryKey(rentid);
    }

    /**
     * 根据出租单号查询出租单信息
     * @param rentid
     * @return
     */
    @Override
    public Rent queryRentByRentId(String rentid) {
        return this.rentMapper.selectByPrimaryKey(rentid);
    }



}
