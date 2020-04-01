package com.yby.sys.controller;

import cn.hutool.Hutool;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.img.ImgUtil;
import com.sun.org.apache.xpath.internal.operations.Mod;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.constast.SysConstast;
import com.yby.sys.domain.LogInfo;
import com.yby.sys.domain.SysUser;
import com.yby.sys.service.LogInfoService;
import com.yby.sys.service.UserService;
import com.yby.sys.utils.ActiveUser;
import com.yby.sys.utils.ResultObj;
import com.yby.sys.utils.WebUtils;
import com.yby.sys.vo.LogInfoVo;
import com.yby.sys.vo.UserVo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

@Controller
@RequestMapping("login")
public class LoginController {

    /**
     * 用户业务层
     */
    @Autowired
    private UserService userService;

    /**
     * 日志业务层
     */
    @Autowired
    private LogInfoService logInfoService;

    /* 跳转到登录页面 */
    @RequestMapping("toLogin")
    public String toLogin() {
        return "system/main/login";
    }

    /* 跳转到首页页面 */
    @RequestMapping("toIndex")
    public String toIndex() {
        return "system/main/index";
    }

    /* 退出登录方法 */
    @RequestMapping("loginOut")
    public String loginOut() {
        WebUtils.getHttpSession().invalidate();
        return "system/main/login";
    }

    /* 登录方法 */
    @RequestMapping("login")
    public String login(UserVo userVo) {
        // 获取验证码
        String code = (String) WebUtils.getHttpSession().getAttribute("code");
        if (code.equals(userVo.getCode())) {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(userVo.getLoginname(), userVo.getPwd());
            try {
                if (null != userVo.getRememberMe() && userVo.getRememberMe() == 1) {
                    // 记住我
                    token.setRememberMe(true);
                    System.out.println("是否记住我"+ token.isRememberMe());
                }
                subject.login(token);
                System.out.println("是否认证成功"+ subject.isAuthenticated());
                ActiveUser activerUser = (ActiveUser) subject.getPrincipal();
                // 把用户储存到session当中
                WebUtils.getHttpSession().setAttribute("user", activerUser.getUser());
                // 记录登录日志 向sys_login_log插入日志
                LogInfoVo logInfo = new LogInfoVo();
                // 设置当前时间
                logInfo.setLogintime(new Date());
                // 设置名称
                logInfo.setLoginname(activerUser.getUser().getRealname() + "-" + activerUser.getUser().getLoginname());
                // 设置ip
                logInfo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
                // 添加日志
                logInfoService.addLogInfo(logInfo);
                // 跳转到主页面
                return "redirect:/login/toIndex";
            } catch (IncorrectCredentialsException e) {
                System.out.println(e.getMessage());
                WebUtils.getHttpSession().setAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
            } catch (UnknownAccountException e) {
                System.out.println(e.getMessage());
                WebUtils.getHttpSession().setAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
            }
            // 认证失败 直接 回到登陆
            return "redirect:index.jsp";
        } else {
            WebUtils.getHttpSession().setAttribute("error", SysConstast.USER_LOGIN_CODE_ERROR_MSG);
            // 验证码错误 直接 回到登陆
            return "redirect:login/toLogin";
        }



/*

        // 获取用户
        SysUser login = this.userService.login(userVo);
        if (null != login) {
            // 把用户储存到session当中
            WebUtils.getHttpSession().setAttribute("user", login);
            // 记录登录日志 向sys_login_log插入日志
            LogInfoVo logInfo = new LogInfoVo();
            // 设置当前时间
            logInfo.setLogintime(new Date());
            // 设置名称
            logInfo.setLoginname(login.getRealname() + "-"+login.getLoginname());
            // 设置ip
            logInfo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
            // 添加日志
            logInfoService.addLogInfo(logInfo);
            // 跳转到主页面
            return "system/main/index";
        } else {
            model.addAttribute("error", SysConstast.USER_LOGIN_ERROR_MSG);
            return "redirect:";
        }*/


        /**
         *
         * System.out.println("来到了登陆方法"+loginname+","+pwd);
         *         Subject subject = SecurityUtils.getSubject();
         *         UsernamePasswordToken token = new UsernamePasswordToken(loginname, pwd);
         *         try {
         *             subject.login(token);
         *             SysUser SysUser = (SysUser) subject.getPrincipal();
         *             WebUtils.getHttpSession().setAttribute("user", SysUser);
         *             // 记录登录日志 向sys_login_log插入日志
         *             LogInfoVo logInfo = new LogInfoVo();
         *             // 设置当前时间
         *             logInfo.setLogintime(new Date());
         *             // 设置名称
         *             logInfo.setLoginname(SysUser.getRealname() + "-" + SysUser.getLoginname());
         *             // 设置ip
         *             logInfo.setLoginip(WebUtils.getHttpServletRequest().getRemoteAddr());
         *             // 添加日志
         *             logInfoService.addLogInfo(logInfo);
         *
         *             return "system/main/index";
         *         } catch (Exception e) {
         *             return "system/main/login";
         *         }
         *
         */


    }

    /**
     * 得到登陆验证码
     * 使用hutool工具包
     *
     * @param response
     * @param session
     * @throws IOException
     */
    @RequestMapping("getCode")
    public void getCode(HttpServletResponse response, HttpSession session) throws IOException {
        //定义图形验证码的长和宽
        // 宽度   高度   验证码数量   线条数
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(116, 36, 4, 5);
        // 储存验证码
        session.setAttribute("code", lineCaptcha.getCode());
        // 声明输出流
        ServletOutputStream outputStream = response.getOutputStream();
        // 输出图片
        /**
         * 1.获取图片地址
         * 2.图片类型
         * 3.流
         */
//        ImgUtil.write();
        // 将 lineCaptcha 图片验证码 输出到 outputStream  格式为 jpeg
        ImageIO.write(lineCaptcha.getImage(), "JPEG", outputStream);
    }


}