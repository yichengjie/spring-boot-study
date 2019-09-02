package com.yicj.study.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.Data;

@Data
public class ExportFieldsInfo {
	// 标题
	private List<String> titleList = new ArrayList<String>();
	// 宽度
	private List<Integer> widthList = new ArrayList<Integer>();
	// 拿到所有列名，以及导出的字段的get方法
	private List<Method> methodList = new ArrayList<Method>();
	private Map<String, Method> convertMethodMap = new HashMap<String, Method>();
	// 得到所有字段
	private Field [] fields  ;
}