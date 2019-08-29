package com.yicj.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.yicj.mybatis.entity.Order;
@Repository
public interface OrderMapper {

	//根据订单号查询订单用户的信息
	Order queryOrderWithUserByOrderNumber(@Param("number") String number) ;
	//根据订单号查询订单用户的信息及订单详
	Order queryOrderWithUserAndDetailByOrderNumber(@Param("number") String number);
	//根据订单号查询订单用户的信息及订单详情及订单详情对应的商品信息
	Order queryOrderWithUserAndDetailItemByOrderNumber(@Param("number") String number);
}
