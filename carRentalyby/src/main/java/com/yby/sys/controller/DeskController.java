package com.yby.sys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/* 控制台的控制器 */
@Controller
@RequestMapping("desk")
public class DeskController {
    /* 跳转到工作台的页面 */
    @RequestMapping("toDescKManager")
    public String toDescKManager(){
        return "system/main/descKManager";
    }
}