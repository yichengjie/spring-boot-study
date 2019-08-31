package com.yicj.study.user.mapper;

import com.yicj.study.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yicj
 * @since 2019-08-31
 */
public interface UserMapper extends BaseMapper<User> {
	//删除所有
	int deleteAll() ;
}
