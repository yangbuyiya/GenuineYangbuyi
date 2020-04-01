package com.yby.bus.mapper;

import com.yby.bus.domain.Car;
import com.yby.bus.vo.CarVo;

import java.util.List;

public interface CarMapper {
    int deleteByPrimaryKey(String carnumber);

    int insert(Car record);

    int insertSelective(Car record);

    Car selectByPrimaryKey(String carnumber);

    int updateByPrimaryKeySelective(Car record);

    int updateByPrimaryKey(Car record);

    List<Car> queryAllCar(Car car);

    List<Car> loadAllRentCar(CarVo carVo);
}