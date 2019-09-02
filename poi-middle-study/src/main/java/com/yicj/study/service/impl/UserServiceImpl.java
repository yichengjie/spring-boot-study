package com.yicj.study.service.impl;

import com.yicj.study.entity.User;
import com.yicj.study.mapper.UserMapper;
import com.yicj.study.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yicj
 * @since 2019-09-01
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
