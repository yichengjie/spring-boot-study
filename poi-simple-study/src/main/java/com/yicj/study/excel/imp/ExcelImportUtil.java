package com.yicj.study.excel.imp;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.yicj.study.excel.ExcelType;

public class ExcelImportUtil {
	
	
	private static List<ImportFieldInfo> parseImportFieldInfo(Class<?> clazz) {
		List<ImportFieldInfo> list = new ArrayList<ImportFieldInfo>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			ImportConf conf = field.getAnnotation(ImportConf.class);
			if (conf == null) {
				continue;
			}
			String fieldName = field.getName();
			Class<?> fieldType = field.getType();
			ImportFieldInfo info = new ImportFieldInfo();
			
			int index = conf.index();
			boolean isConvert = conf.isConvert();
			info.setFieldType(fieldType);
			info.setFieldName(fieldName);
			info.setIndex(index);
			Method setterMethod = getSetterMethodByField(field, clazz);
			info.setSetterMethod(setterMethod);
			if (isConvert) {
				Method convertMethod = getConvertMethodByField(field, clazz);
				info.setConvertMethod(convertMethod);
			}
			list.add(info);
		}
		return list;
	}

	public static List<?> importExcel(InputStream inputStream, Class<?> clazz, ExcelType type){
		// 存储excel数据
		List<Object> list = new ArrayList<Object>();
		Workbook wookbook = getWorkbook(inputStream, type);
		// 得到一个工作表
		Sheet sheet = wookbook.getSheetAt(0);
		// 获取行总数
		int rows = sheet.getLastRowNum() + 1;
		List<ImportFieldInfo> fieldInfos = parseImportFieldInfo(clazz);
		// 从第2行开始遍历
		for (int i = 1; i < rows; i++) {
			// 获取excel中的一行
			Row row = sheet.getRow(i);
			Object obj = readContentRow(row, fieldInfos, clazz);
			list.add(obj) ;
		}
		return list;
	}

	private static Object readContentRow(Row row, List<ImportFieldInfo> fieldInfos, Class<?> clazz) {
		try {
			Object obj = clazz.newInstance();
			for(ImportFieldInfo info : fieldInfos) {
				Cell cell = row.getCell(info.getIndex());
				String cellValueStr = cell.getStringCellValue();
				Method convertMethod = info.getConvertMethod();
				if(convertMethod != null) {
					convertMethod.invoke(obj, cellValueStr) ;
				}else {
				   Class<?> fieldType = info.getFieldType();
				   Object value = getTypeValue(fieldType,cellValueStr) ;
				   info.getSetterMethod().invoke(obj, value) ;	
				}
			}
			return obj ;
		} catch (Exception e) {
			throw new RuntimeException("填充数据到对象报错!",e) ;
		}
	}

	private static Object getTypeValue(Class<?> type, String content) {
		if (type == int.class || type == Integer.class) {
			return Integer.parseInt(content);
		} else if (type == Double.class || type == double.class) {
			return Double.parseDouble(content);
		} else if (type == Float.class || type == float.class) {
			return Float.parseFloat(content);
		}
		return content ;	
	}

	private static Workbook getWorkbook(InputStream is, ExcelType type) {
		// 根据excel文件版本获取工作簿
		Workbook wookbook = null ;
		switch (type) {
		case xlsx:
			wookbook = xlsx(is);
			break;
		default:
			wookbook = xls(is);
			break;
		}
		return wookbook ;
	}

	/**
	 * 对excel 2003处理
	 */
	private static Workbook xls(InputStream is) {
		try {
			// 得到工作簿
			return new HSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException("创建工作簿");
		}
	}

	/**
	 * 对excel 2007处理
	 */
	private static Workbook xlsx(InputStream is) {
		try {
			// 得到工作簿
			return new XSSFWorkbook(is);
		} catch (IOException e) {
			throw new RuntimeException("创建工作簿");
		}
	}

	// 通过字段名获取get方法名称 : getXXX
	private static Method getSetterMethodByField(Field field, Class<?> clazz) {
		StringBuffer methodName = new StringBuffer("set");
		try {
			String fieldName = field.getName();
			methodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
			Method getterMethod = clazz.getMethod(methodName.toString(), new Class[] { field.getType() });
			return getterMethod;
		} catch (Exception e) {
			throw new RuntimeException("获取" + methodName.toString() + "方法报错!", e);
		}
	}

	//注意convert仅接收string类型参数
	// 通过字段名获取conver方法名称: getXXXConvert
	private static Method getConvertMethodByField(Field field, Class<?> clazz) {
		StringBuilder methodName = new StringBuilder("set");
		try {
			String fieldName = field.getName();
			methodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1)).append("Convert");
			Method method = clazz.getMethod(methodName.toString(), new Class[] { String.class });
			return method;
		} catch (Exception e) {
			throw new RuntimeException("获取" + methodName.toString() + "方法报错!", e);
		}
	}

	public static void main(String[] args) throws IOException {
		
	}
}
