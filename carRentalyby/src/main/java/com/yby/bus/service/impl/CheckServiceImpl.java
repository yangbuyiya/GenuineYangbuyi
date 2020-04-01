package com.yby.bus.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yby.bus.domain.Car;
import com.yby.bus.domain.Check;
import com.yby.bus.domain.Customer;
import com.yby.bus.domain.Rent;
import com.yby.bus.mapper.CarMapper;
import com.yby.bus.mapper.CheckMapper;
import com.yby.bus.mapper.CustomerMapper;
import com.yby.bus.mapper.RentMapper;
import com.yby.bus.service.CheckService;
import com.yby.bus.vo.CheckVo;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.SysUser;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.RandomUtils;
import com.yby.sys.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author TeouBle
 */
@Service
public class CheckServiceImpl implements CheckService {

    private final CheckMapper checkMapper;
    private final RentMapper rentMapper;
    private final CarMapper carMapper;
    private final CustomerMapper customerMapper;

    @Autowired
    public CheckServiceImpl(CheckMapper checkMapper, RentMapper rentMapper, CarMapper carMapper, CustomerMapper customerMapper) {
        this.checkMapper = checkMapper;
        this.rentMapper = rentMapper;
        this.carMapper = carMapper;
        this.customerMapper = customerMapper;
    }


    /**
     * 根据出租单号查询出租单信息
     *
     * @param rentid
     * @return
     */
    @Override
    public Map<String, Object> initCheckFormData(String rentid) {
        // 1.查询出租单
        Rent rent = this.rentMapper.selectByPrimaryKey(rentid);
        // 2.查询客户
        Customer customer = this.customerMapper.selectByPrimaryKey(rent.getIdentity());
        // 3.查询车辆
        Car car = this.carMapper.selectByPrimaryKey(rent.getCarnumber());
        // 4.组装check
        Check check = new Check();
        // 出租单号
        check.setCheckid(RandomUtils.createRandomStringUseTime(SysConstast.CAR_ORDER_JC));
        // 生成检查单号
        check.setRentid(rent.getRentid());
        // 检查时间
        check.setCheckdate(new Date());
        // 检查员
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        check.setOpername(user.getRealname());

        // 返回数据/
        HashMap<String, Object> map = new HashMap<>();
        map.put("rent", rent);
        map.put("check", check);
        map.put("car", car);
        map.put("customer", customer);
        return map;
    }

    /**
     * '
     * 保存检查单信息
     *
     * @param checkVo
     */
    @Override
    public void addCheck(CheckVo checkVo) {
        // 检查单里面有汽车出租单号
        this.checkMapper.insertSelective(checkVo);
        // 更改出租单状态
        Rent rent = this.rentMapper.selectByPrimaryKey(checkVo.getRentid());
        // 设置已归还
        rent.setRentflag(SysConstast.RENT_BACK_TRUE);
        this.rentMapper.updateByPrimaryKeySelective(rent);
        // 更改汽车的状态
        Car car = new Car();
        // 汽车id
        car.setCarnumber(rent.getCarnumber());
        // 设置未出租
        car.setIsrenting(SysConstast.RENT_CAR_FALSE);
        this.carMapper.updateByPrimaryKeySelective(car);
    }

    /**
     * 查询检查单
     *
     * @param checkVo
     * @return 返回查询出的数据
     */
    @Override
    public DataGridView queryAllCheck(CheckVo checkVo) {
        // 开启分页
        Page<Object> pageHelper = PageHelper.startPage(checkVo.getPage(), checkVo.getLimit());
        // 调用mapper查询检查单信息
        List<Check> checks = this.checkMapper.queryAllCheck(checkVo);
        // 封装一下返回
        return new DataGridView(pageHelper.getTotal(), checks);
    }

    /**
     * 更新检查单
     *
     * @param checkVo
     * @return 返回响应
     */
    @Override
    public void updateCheck(CheckVo checkVo) {
        this.checkMapper.updateByPrimaryKeySelective(checkVo);
    }
}