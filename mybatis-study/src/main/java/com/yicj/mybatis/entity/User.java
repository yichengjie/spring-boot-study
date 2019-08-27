package com.yicj.mybatis.entity;

import java.util.Date;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class User {
	private String id ;
	private String userName ;
	private String password ;
	private String name ;
	private Integer age ;
	private Integer sex ;
	private Date birthday ;
	private Date created ;
	private Date updated ;
}
