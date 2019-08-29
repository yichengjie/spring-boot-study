package com.yicj.study.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.yicj.study.entity.User;

@Repository
public interface UserMapper {
	
	List<User> selectUserAll() ;
}
