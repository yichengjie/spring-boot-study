package com.yicj.mybatis.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.yicj.mybatis.entity.User;

public interface UserMapper {
	//根据表名查询用户信息（直接使用注解指定传入参数名称）
	public User login(@Param("userName") String userName, 
			@Param("password") String password) ;
	//根据表名查询用户信息（直接使用注解指定传入参数名称）
	public List<User> queryUserByTableName(
			@Param("tableName") String tableName) ;
	// 根据Id查询用户信息
	public User queryUserById(String id) ;
	//查询所有用户信息
	public List<User> queryUserAll() ;
	//新增用户信息
	public void insertUser(User user) ;
	// 根据id更新用户信息
	public void updateUser(User user) ;
	//根据id删除用户信息
	public void deleteUserById(String id) ;
}
