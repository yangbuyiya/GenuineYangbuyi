package com.yby.sys.inerceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yby.sys.domain.SysUser;
import com.yby.sys.utils.WebUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/* 登录判断当前是否 */
public class LoginInterceptor implements HandlerInterceptor {

    /* 程序执行之前访问 */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("进入拦截器============================================");
        /* 储存当前用户 */
        SysUser user = (SysUser) WebUtils.getHttpSession().getAttribute("user");
        System.out.println("正在获取当前用户..................");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("==================================");
        System.out.println("当前session用户是" + user.getRealname());
        System.out.println("已储存当前session用户通过---------user---------进行获取该session");

        return user != null;
    }

}