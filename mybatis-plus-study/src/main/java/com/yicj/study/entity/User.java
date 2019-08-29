package com.yicj.study.entity;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	private Long id ;
	private String name ;
	private Integer age ;
	private String email ;
	private Long manager_id ;
	private Date create_time ;
}
