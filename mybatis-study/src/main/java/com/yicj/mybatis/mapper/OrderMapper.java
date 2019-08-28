package com.yicj.mybatis.mapper;

import org.apache.ibatis.annotations.Param;

import com.yicj.mybatis.entity.Order;

public interface OrderMapper {

	//根据订单号查询订单用户的信息
	Order queryOrderWithUserByOrderNumber(@Param("number") String number) ;
	
}
