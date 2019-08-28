package com.yicj.mybatis.entity;

import java.util.Date;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class OrderUser {
	//order的信息
	private Integer id;
	private Long userId;
	private String orderNumber;
	private Date created;
	private Date updated;
	
	private String userName;
	private String password;
	private String name;
	private Integer age;
	private Integer sex;
	private Date birthday;
//	private Date created;
//	private Date updated;
}
