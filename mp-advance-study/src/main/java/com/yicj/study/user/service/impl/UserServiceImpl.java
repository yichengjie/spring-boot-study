package com.yicj.study.user.service.impl;

import com.yicj.study.user.entity.User;
import com.yicj.study.user.mapper.UserMapper;
import com.yicj.study.user.service.IUserService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yicj
 * @since 2019-08-31
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

	@Autowired
	private UserMapper userMapper ;
	
	@Override
	public List<User> listByMyWrapper(Wrapper<User> userWrapper) {
		return this.userMapper.selectByMyWrapper(userWrapper);
	}

}
