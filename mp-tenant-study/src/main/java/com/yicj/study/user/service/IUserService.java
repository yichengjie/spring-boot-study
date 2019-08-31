package com.yicj.study.user.service;

import com.yicj.study.user.entity.User;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
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
	List<User> listByMyWrapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);
}
