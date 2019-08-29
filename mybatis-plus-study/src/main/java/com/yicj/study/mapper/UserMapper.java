package com.yicj.study.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yicj.study.entity.User;

@Repository
public interface UserMapper extends BaseMapper<User> {
	
	List<User> selectUserAll() ;
}
