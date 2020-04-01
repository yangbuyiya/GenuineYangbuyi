package com.yby.sys.filter;

import com.yby.sys.utils.ActiveUser;
import com.yby.sys.utils.ActiveUser;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 自定义的过滤器记住我的功能处理session内空丢失的问题
 * @author 杨不易
 *
 */
public class RememberMeFilter extends FormAuthenticationFilter {

	@Override
	protected boolean onAccessDenied(ServletRequest request, ServletResponse response, Object mappedValue)
			throws Exception {
		//得到主体
		Subject subject = SecurityUtils.getSubject();
		//得到session
		Session session = subject.getSession();
		if(!subject.isAuthenticated()&&subject.isRemembered()&&session.getAttribute("user")==null) {
			ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
			session.setAttribute("user", activeUser.getUser());
		}
		return true;//放行
	}
}
