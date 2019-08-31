package com.yicj.study.user.service;

import com.yicj.study.user.entity.User;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author yicj
 * @since 2019-08-31
 */
public interface IUserService extends IService<User> {
	
	int deleteAll() ;
	
	int insertBatchSomeColumn(List<User> list) ;
}
