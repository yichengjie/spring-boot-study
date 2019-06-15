package com.yicj.study.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 用户类
 */
@ApiModel(value="用户对象模型")
public class User {
	@ApiModelProperty(value="id" ,required=true)
	private Long id;
	@ApiModelProperty(value="用户姓名" ,required=true)
    private String name;
    private int age ;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
    
}
