package com.yicj.study.excel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class FieldsInfo {
	// 标题
	private List<String> titleList = new ArrayList<String>();
	// 宽度
	private List<Integer> widthList = new ArrayList<Integer>();
	// 拿到所有列名，以及导出的字段的get方法
	private List<Method> methodList = new ArrayList<Method>();
	private Map<String, Method> convertMethodMap = new HashMap<String, Method>();
	// 得到所有字段
	private Field [] fields  ;
	// 是否求和配置
	// 是否求和配置
	private boolean isSum ;
	private List<BigDecimal> sumList = new ArrayList<BigDecimal>() ;
	private List<Boolean> isSumList = new ArrayList<Boolean>();
	
	private List<Integer> scaleList = new ArrayList<Integer>();
	private List<Boolean> isMergeList = new ArrayList<Boolean>();
	private List<Method> mergeFlagList = new ArrayList<Method>();
}