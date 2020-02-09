package com.yicj.study.excel;

import java.lang.reflect.Method;
import lombok.Data;

@Data
public class ExportFieldInfo{
	private String  fieldName ; //字段名
	private String  title ; //字段描述，对应excel表头标题
	private Integer width ; //每列宽度
	private Method  getterMethod ;
	private Method  convertMethod ;
	private Method  mergeKeyMethod ;//依赖哪一列判断是否可合并
}