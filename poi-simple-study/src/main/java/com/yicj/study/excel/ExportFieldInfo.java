package com.yicj.study.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import lombok.Data;

@Data
public class ExportFieldInfo{
	private Integer index ;
	private Field   field ;
	private String  title ;
	private Integer width ;
	private Method  getterMethod ;
	private Method  convertMethod ;
}