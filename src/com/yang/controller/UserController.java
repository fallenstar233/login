package com.yang.controller;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yang.entity.User;
import com.yang.service.UserService;
import com.yang.utils.JsonResult;

/**
 * 用户登录控制器
 */
@Controller
@RequestMapping("user")
public class UserController {
	
	@Resource
	private UserService userService;
	
	//登录时调用的方法
	@RequestMapping("login.do")
	@ResponseBody
	public JsonResult<User> login(String name,String password,HttpServletResponse response){
		User user = userService.login(name, password);
		Cookie cookie = new Cookie("token", user.getToken());
		cookie.setPath("/"); 
		response.addCookie(cookie);
		return new JsonResult<User>(user);
	}
}
