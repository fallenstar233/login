package com.yang.service;

import com.yang.entity.User;

public interface UserService {
	
	User login(String name, String password)throws UserNameException,PasswordException;
	
	boolean checkToken(String userId,String token);
}










