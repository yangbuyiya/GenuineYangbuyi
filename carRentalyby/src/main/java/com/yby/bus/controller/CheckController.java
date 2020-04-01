package com.yby.bus.controller;

import com.yby.bus.domain.Check;
import com.yby.bus.domain.Rent;
import com.yby.bus.service.CheckService;
import com.yby.bus.service.RentService;
import com.yby.bus.vo.CheckVo;
import com.yby.sys.utils.DataGridView;
import com.yby.sys.utils.ResultObj;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

/**
 * 汽车入库和检查单管理的控制器
 *
 * @author TeouBle
 */

@RestController
@RequestMapping("check")
public class CheckController {

    @Autowired
    private RentService rentService;
    @Autowired
    private CheckService checkService;

    /* ----------------------------车辆入库管理----------------------------- */


    /**
     * 根据出租单号查询出租单信息
     */
    @RequestMapping("checkRentExist")
    public Rent checkRentExist(String rentid) {
        /*思路：当输入的出租单号查询出来的为断空
        * 则判断 出租单号不存在
        * 如不为空 则判断 是否已经归还 rentflag 是否对于1
        * 等于则提示 您输入的出租单号相关车辆已经归还，无需再入库
        * 否则返回查询出来的数据
        * */
        return this.rentService.queryRentByRentId(rentid);
    }

    /**
     * 根据出租单号加载检查单的表单数据
     */
    @RequestMapping("initCheckFormData")
    public Map<String, Object> initCheckFormData(String rentid) {
        return this.checkService.initCheckFormData(rentid);
    }

    /**
     * 保存检查单数据 saveCheck
     */
    @RequestMapping("saveCheck")
    public ResultObj saveCheck(CheckVo checkVo) {
        try {
            checkVo.setCreatetime(new Date());
            this.checkService.addCheck(checkVo);
            return ResultObj.ADD_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.ADD_ERROR;
        }
    }

    /* ----------------------------检查单管理----------------------------- */

    /**
     * 加载检查管理列表 loadAllCheck
     *
     * @param checkVo
     * @return
     */
    @RequestMapping("loadAllCheck")
    public DataGridView loadAllCheck(CheckVo checkVo) {
        System.out.println("进来啦？");
        return this.checkService.queryAllCheck(checkVo);
    }


    /**
     * 更新检查单
     * @param checkVo
     * @return 返回响应
     */
    @RequestMapping("updateCheck")
    public ResultObj updateCheck(CheckVo checkVo){
        try {
            this.checkService.updateCheck(checkVo);
            return ResultObj.UPDATE_SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return ResultObj.UPDATE_ERROR;
        }
    }


}