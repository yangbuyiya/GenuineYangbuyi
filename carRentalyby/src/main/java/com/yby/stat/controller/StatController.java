package com.yby.stat.controller;

import com.yby.bus.domain.Car;
import com.yby.bus.domain.Check;
import com.yby.bus.domain.Customer;
import com.yby.bus.domain.Rent;
import com.yby.bus.service.CarService;
import com.yby.bus.service.CustomerService;
import com.yby.bus.service.RentService;
import com.yby.bus.service.impl.CheckServiceImpl;
import com.yby.bus.vo.CustomerVo;
import com.yby.bus.vo.RentVo;
import com.yby.stat.domain.BaseEntity;
import com.yby.stat.service.StatService;
import com.yby.stat.utils.ExportCustomerUtils;
import com.yby.stat.utils.ExportRentUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 统计分析
 *
 * @author TeouBle
 */
@Controller
@RequestMapping("stat")
public class StatController {

    @Autowired
    private StatService statService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CarService carService;
    @Autowired
    private RentService rentService;
    @Autowired
    private CheckServiceImpl checkService;

    /**
     * 跳转到客户地区统计页面
     */
    @RequestMapping("toCustomerAreaStat")
    public String toCustomerAreaStat() {
        return "stat/customerAreaStat";
    }


    /* 加载客户地区数据 */
    @RequestMapping("yby/loadCustomerAreaStat")
    @ResponseBody
    public List<BaseEntity> loadCustomerAreaStatJson() {
        System.out.println("进来啦");
        return this.statService.loadCustomerAreaStatJson();
    }

    /**
     * 跳转到业务员年度业务统计
     *
     * @return
     */
    @RequestMapping("toOpernameYearGradeStat")
    public String toOpernameYearGradeStat() {
        return "stat/opernameYearGradeStat";
    }


    /**
     * 加载业务员年度业务统计数据
     *
     * @return
     */
    @RequestMapping("loadOpernameYearGradeStatJson")
    @ResponseBody
    public Map<String, Object> loadOpernameYearGradeStatJson(String year) {
        List<BaseEntity> entities = this.statService.loadOpernameYearGradeStatList(year);
        Map<String, Object> map = new HashMap<String, Object>();
        // 图像数值
        List<String> names = new ArrayList<String>();
        List<Double> values = new ArrayList<Double>();
        for (BaseEntity baseEntity : entities) {
            names.add(baseEntity.getName());
            values.add(baseEntity.getValue());
        }
        map.put("name", names);
        map.put("value", values);
        return map;
    }

    /**
     * 跳转到公司年度业务统计
     *
     * @return
     */
    @RequestMapping("toCompanyYearGradeStat")
    public String toCompanyYearGradeStat() {
        return "stat/companyYearGradeStat";
    }

    /**
     * 加载公司年度业务统计数据
     *
     * @return
     */
    @RequestMapping("loadCompanyYearGradeStatJson")
    @ResponseBody
    public List<Double> loadCompanyYearGradeStatJson(String year) {
        List<Double> entities = this.statService.loadCompanyYearGradeStatList(year);
        // 如果查询的数据有null  则设置为 0元额度
        for (int i = 0; i < entities.size(); i++) {
            if (null == entities.get(i)) {
                entities.set(i, 0.0);
            }
        }
        return entities;
    }

    /**
     * 导出客户xls
     * 下载 ResponseEntity<Object>  返回值
     *
     * @param customerVo
     */
    @RequestMapping("exportCustomer")
    public ResponseEntity<Object> exportCustomer(CustomerVo customerVo, HttpServletResponse response) {
        List<Customer> customers = this.customerService.queryAllCustomerForList(customerVo);
        String fileName = "汽车客户数据.xls";
        String sheetName = "客户数据";

        // 生成工作簿
        ByteArrayOutputStream bos = ExportCustomerUtils.exportCustomer(customers, sheetName);

        /* 下载excel */
        return StatController.downloadExcel(fileName, bos);
    }


    /**
     * 导出出租单xls
     * 下载 ResponseEntity<Object>  返回值
     *
     * @param rentVo
     */
    @RequestMapping("exportRent")
    public ResponseEntity<Object> exportRent(RentVo rentVo) {

        // 1.根据出租单号查询出租单信息
        Rent rent = rentService.queryRentByRentId(rentVo.getRentid());
        // 获取全部信息
        Map<String, Object> map = checkService.initCheckFormData(rent.getRentid());
        Check check = (Check) map.get("check");
        Car car = (Car) map.get("car");
        Customer customer = (Customer) map.get("customer");
        // 2.根据出租单信息当中的身份证号 查询客户信息
        String fileName = customer.getCustname() + "-出租单数据.xls";
        String sheetName = customer.getCustname() + "的出租单";




//Check check, Car car, Rent rent, Customer customer

        // 生成出租单  生成工作簿
        ByteArrayOutputStream exportRent = ExportRentUtils.exportRent(
            check, car,
            rent, customer, sheetName);
        // 下载
        return StatController.downloadExcel(fileName, exportRent);

    }


    /**
     * 下载excel文件方法
     *
     * @param fileName 文件名称
     * @param bos      工作簿
     * @return 响应实体
     */
    public static ResponseEntity<Object> downloadExcel(String fileName, ByteArrayOutputStream bos) {
        /* 下载excel */
        try {
            //处理文件名乱码
            fileName = URLEncoder.encode(fileName, "UTF-8");
            //创建 封装响应头信息的对象
            HttpHeaders headers = new HttpHeaders();
            //封装响应内容类型(APPLICATION_OCTET_STREAM 响应的内容不限定)
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            //设置下载的文件的名称
            headers.setContentDispositionFormData("attachment", fileName);
            // 返回响应
            return new ResponseEntity<Object>(bos.toByteArray(), headers, HttpStatus.CREATED);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }


}