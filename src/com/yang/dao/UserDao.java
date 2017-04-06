package com.yang.dao;

import java.util.Map;

import com.yang.entity.User;

public interface UserDao {
	
	User findUserById(String id);
	
	User findUserByName(String name);

	void updateUser(Map<String, Object> data);
	
}
