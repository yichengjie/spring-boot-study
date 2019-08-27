package com.yicj.mybatis.dao;

import java.util.List;

import com.yicj.mybatis.entity.User;

public interface UserDao {
	
	public User queryUserById(String id) ;
	
	public List<User> queryUserAll() ;
	
	public void insertUser(User user) ;
	
	public void updateUser(User user) ;
	
	public void deleteUser(String id) ;

}
