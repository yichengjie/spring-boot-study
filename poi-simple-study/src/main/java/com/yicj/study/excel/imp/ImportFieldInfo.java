package com.yicj.study.excel.imp;

import java.lang.reflect.Method;

import lombok.Data;

@Data
public class ImportFieldInfo {
	//列索引
	private int index ;
	//字段类型
	private Class<?> fieldType ;
	//字段名称
	private String fieldName ;
	//setter方法
	private Method setterMethod ;
	//convert方法
	private Method convertMethod ;
	
	
}
