package com.yicj.mybatis.dao.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.yicj.mybatis.dao.UserDao;
import com.yicj.mybatis.entity.User;

public class UserDaoImpl implements UserDao{
	
	private SqlSession sqlSession ;
	 
	public UserDaoImpl(SqlSession sqlSession) {
		this.sqlSession = sqlSession ;
	}

	@Override
	public User queryUserById(String id) {
		return this.sqlSession.selectOne("com.yicj.mybatis.dao.UserDao.queryUserById",id);
	}

	@Override
	public List<User> queryUserAll() {
		return this.sqlSession.selectList("com.yicj.mybatis.dao.UserDao.queryUserAll");
	}

	@Override
	public void insertUser(User user) {
		this.sqlSession.insert("com.yicj.mybatis.dao.UserDao.insertUser",user) ;
	}

	@Override
	public void updateUser(User user) {
		this.sqlSession.update("com.yicj.mybatis.dao.UserDao.updateUser",user) ;
	}

	@Override
	public void deleteUser(String id) {
		this.sqlSession.delete("com.yicj.mybatis.dao.UserDao.deleteUser",id) ;
	}
}
