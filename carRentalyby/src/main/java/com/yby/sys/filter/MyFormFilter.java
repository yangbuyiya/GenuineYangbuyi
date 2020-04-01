package com.yby.sys.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yby.sys.utils.AjaxRes;
import lombok.SneakyThrows;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.servlet.OncePerRequestFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 登陆验证
 *
 * @author TeouBle
 */
public class MyFormFilter extends FormAuthenticationFilter {

    /* 当认证成功时调用 */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        /* 响应给浏览器 */
        response.setContentType("text/html;charset=utf-8");
        System.out.println("认证成功");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMsg("登录成功");
        /* 要把对象转换为json格式的字符串 */
        String s = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(s);
        return false;
    }

    /* 当认证失败时 调用 */
    @SneakyThrows
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {

        System.out.println("认证失败");
        response.setContentType("text/html;charset=utf-8");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        // 判断异常是否为空
        if (e != null) {
//             获取异常名称
            String s = e.getClass().getName();
            if (s.equals(UnknownAccountException.class.getName())) {
//                 没有账号
                ajaxRes.setMsg("用户名不正确,如果没有请注册");
            } else if (s.equals(IncorrectCredentialsException.class.getName())) {
                System.out.println("异常:" + e);
//                 密码错误
                ajaxRes.setMsg("密码不正确");
            } else {
                System.out.println("异常:" + e);
//                 未知异常
                ajaxRes.setMsg("未知错误");
            }
        }
//         响应给浏览器
//         要把对象转换为json格式的字符串
        String s = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(s);
        return false;
    }
}