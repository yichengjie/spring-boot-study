package com.yicj.study.excel.exp;

import java.lang.reflect.Method;
import lombok.Data;

@Data
public class ExportFieldInfo{
	private Integer index ;
	private String  title ;
	private Integer width ;
	private String  name ;
	private Method  getterMethod ;
	private Method  convertMethod ;
}