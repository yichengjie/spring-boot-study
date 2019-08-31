package com.yicj.study.user.service.impl;

import com.yicj.study.user.entity.User;
import com.yicj.study.user.mapper.UserMapper;
import com.yicj.study.user.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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

}
