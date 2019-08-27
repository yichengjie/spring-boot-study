package com.yicj.mybatis.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Before;
import org.junit.Test;

import com.yicj.mybatis.common.MybatisUtils;
import com.yicj.mybatis.dao.impl.UserDaoImpl;
import com.yicj.mybatis.entity.User;

public class UserDaoTest2 {
	public UserDao userDao;
	public SqlSession sqlSession;
	
	
	@Before
    public void setUp() throws Exception {
        // mybatis-config.xml
        String resource = "mybatis-config.xml";
        SqlSessionFactory factory = MybatisUtils.createSqlSessionFactory(resource);
        this.sqlSession = factory.openSession();
        this.userDao = new UserDaoImpl(sqlSession);
    }
	
	@Test
	public void queryUserById() {
		User user = this.userDao.queryUserById("100");
		System.out.println(user);
	}
	
	@Test
	public void queryUserAll() {
		List<User> users = this.userDao.queryUserAll();
		users.forEach(user -> System.out.println(user));
	}
	
	@Test
	public void insertUser() {
		User user = new User() ;
		user.setAge(16);
		user.setBirthday(new Date("1990/09/02"));
		user.setName("大鹏");
		user.setPassword("123456");
		user.setSex(1);
		user.setUserName("evan");
		this.userDao.insertUser(user);
		this.sqlSession.commit(); 
	}
	
	@Test
	public void updateUser() {
		User user = new User() ;
		user.setName("静鹏");
		user.setPassword("654321");
		user.setSex(1);
		user.setUserName("evanjin");
		user.setId("1");
		this.userDao.updateUser(user);
		this.sqlSession.commit();
	}
	
	@Test
	public void deleteUser() {
		this.userDao.deleteUser("1");
		this.sqlSession.commit();
	}
	
}
