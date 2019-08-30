package com.yicj.study.user.mapper;

import com.yicj.study.user.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author jobob
 * @since 2019-08-30
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
	
	List<User> selectAllUser() ;
}
