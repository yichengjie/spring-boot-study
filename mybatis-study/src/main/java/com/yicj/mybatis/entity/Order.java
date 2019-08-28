package com.yicj.mybatis.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Order {
	private Integer id;
	private Long userId;
	private String orderNumber;
	private Date created;
	private Date updated;
	private User user ;
	private List<OrderDetail> detailList = new ArrayList<OrderDetail>() ;
}
