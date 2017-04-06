package com.yang.test;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.yang.dao.UserDao;
import com.yang.entity.User;
import com.yang.utils.Utils;

public class DataTest {
	ClassPathXmlApplicationContext ctx;
	@Before
	public void init(){
		ctx = new ClassPathXmlApplicationContext("applicationContext-dao.xml");
	}
	@Test
	public void testDataSource(){
		DataSource ds = ctx.getBean("dataSource", DataSource.class);
		System.out.println("ds..."+ds); 
	}
	
	@Test
	public void testFindUserById(){
		UserDao userdao = ctx.getBean("userDao",UserDao.class);
		User user = userdao.findUserByName("root");
		System.out.println(user);
	}
	
	@Test
	public void testUtils(){
		UserDao userdao = ctx.getBean("userDao",UserDao.class);
		Utils u = new Utils();
		User user = userdao.findUserByName("root");
		String pwd = user.getPassword();
		String s = u.crypt(pwd);
		System.out.println(pwd);
	}
}
