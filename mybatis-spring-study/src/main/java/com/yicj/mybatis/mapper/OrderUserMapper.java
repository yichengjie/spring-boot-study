package com.yicj.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.yicj.mybatis.entity.OrderUser;

public interface OrderUserMapper {
	
	OrderUser queryOrderUserByOrderNumber(@Param("number")String number) ;
}
