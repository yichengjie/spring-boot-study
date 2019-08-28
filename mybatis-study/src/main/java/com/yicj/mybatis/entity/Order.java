package com.yicj.mybatis.entity;

import java.util.Date;
import lombok.Data;

@Data
public class Order {
	private Integer id;
	private Long userId;
	private String orderNumber;
	private Date created;
	private Date updated;
}
