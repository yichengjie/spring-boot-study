package com.yicj.study.user.service.impl;

import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yicj.study.user.entity.User;
import com.yicj.study.user.mapper.UserMapper;
import com.yicj.study.user.service.IUserService;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yicj
 * @since 2019-08-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
}
