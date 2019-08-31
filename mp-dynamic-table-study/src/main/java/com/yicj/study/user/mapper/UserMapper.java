package com.yicj.study.user.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.baomidou.mybatisplus.annotation.SqlParser;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.yicj.study.user.entity.User;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author yicj
 * @since 2019-08-31
 */

public interface UserMapper extends BaseMapper<User> {
	
	/**
    *
    * 如果自定义的方法还希望能够使用MP提供的Wrapper条件构造器，则需要如下写法
    *
    * @param userWrapper
    * @return
    */
   @SqlParser(filter=true)
   @Select("SELECT * FROM user ${ew.customSqlSegment}")
   List<User> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);
}
