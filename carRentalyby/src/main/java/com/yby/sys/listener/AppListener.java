package com.yby.sys.listener;

import org.junit.Test;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class AppListener implements ServletContextListener {

    @Override
    public void contextDestroyed(ServletContextEvent arg0) {

    }

    @Override
    public void contextInitialized(ServletContextEvent arg0) {
        //取到ServletContext
        // 储存当前绝对路径
        ServletContext context = arg0.getServletContext();
        context.setAttribute("yby", context.getContextPath());
        System.err.println("---------Servlet容器创建成功 yby被放到ServletContext作用域-------");
    }

}
