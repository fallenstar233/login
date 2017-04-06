package com.yang.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yang.service.UserService;
/**
 * 拦截请求
 */
public class LoginFilter implements Filter {
	//一些初始化
	private ServletContext sc;
	private ApplicationContext ctx;
	private UserService userService;
	@Override
	public void init(FilterConfig cfg) throws ServletException {
		sc = cfg.getServletContext();
		ctx = WebApplicationContextUtils.getWebApplicationContext(sc);
		userService = ctx.getBean("userService",UserService.class);
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		String path=request.getRequestURI();
		path = path.substring(path.indexOf('/', 1));
		//拦截success.html并处理
		if(path.matches(".*success\\.html$")){
			checkLogin(request,response,chain);
			return;
		}	
		//其余的请求给与放行，比如登录页面
		chain.doFilter(request, response); 
	}
	
	private void checkLogin(HttpServletRequest request, HttpServletResponse response, FilterChain chain) 
			throws IOException, ServletException {
		//获得cookie
		String token = getCookie(request, "token");
		String userId = getCookie(request,"userId");
		//根据userId比较token,如果cookie里的token和数据库里的相同则放行
		if(userService.checkToken(userId, token)){
			chain.doFilter(request, response);
			return;
		}
		//重定向到登录页
		String path = request.getContextPath() + "/index.html";
		response.sendRedirect(path);
	}
	
	private String getCookie(HttpServletRequest request, String cookieName) {
		Cookie[] cookies = request.getCookies();
		if(cookies!=null){
			for (Cookie cookie : cookies) {
				if(cookieName.equals(cookie.getName())){
					return cookie.getValue();
				}
			}
		}
		return null;
	}

	@Override
	public void destroy() {
	}
}
