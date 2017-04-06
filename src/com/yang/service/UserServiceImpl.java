package com.yang.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.annotation.Resource;
import javax.swing.plaf.synth.SynthSpinnerUI;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yang.dao.UserDao;
import com.yang.entity.User;
import com.yang.utils.Utils;

/**
 * 对用户名 密码 token进行检验
 */
@Service("userService")
public class UserServiceImpl implements UserService{
	
	@Resource
	private UserDao userDao;
	
	@Transactional
	public User login(String name, String password) throws UserNameException, PasswordException {
		
		if(name==null||name.trim().isEmpty()){
			throw new UserNameException("名不能空");
		}
		
		String reg = "^\\w{3,10}$";
		if(! name.matches(reg)){
			throw new UserNameException("不合规");
		}
		if(password==null||password.trim().isEmpty()){
			throw new PasswordException("不能空");
		}
		if(! password.matches(reg)){
			throw new PasswordException("不合规");
		}
		User user=userDao.findUserByName(name);
		if(user==null){
			throw new UserNameException("错误");
		}
		String md5=Utils.crypt(password);
		if(user.getPassword().equals(md5)){
			String token=UUID.randomUUID().toString();
			user.setToken(token);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("id", user.getId());
			data.put("token", token);
			userDao.updateUser(data);
			return user;
		}
		throw new PasswordException("密码错");
	}
		
	@Transactional
	public boolean checkToken(String userId, String token) {
		if(userId==null || userId.trim().isEmpty()){
			System.out.println(userId);
			return false;
		}
		if(token==null || token.trim().isEmpty()){
			System.out.println(token);
			return false;
		}
		User user = userDao.findUserById(userId);
		if(user==null){
			return false; 
		}
		return token.equals(user.getToken());
	}
}





