package com.yicj.study.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelExportUtil {
	
	/**
	 * 生成导出Excel
	 * @param title
	 * @param dataSet
	 * @param out
	 */
	public static void exportExcel(String title, List<?> dataList, OutputStream out) {
		Workbook workbook = null;
		try {
			// 首先检查数据看是否是正确的
			checkDataValid(title, dataList, out);
			// 声明一个工作薄
			workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			// 获取第一个对象的class
			Class<?> dataClass = dataList.get(0).getClass();
			// 通过class解析注解的各字段信息
			List<ExportFieldInfo> fieldInfos = parseExportFieldInfo(dataClass);
			// 产生表格标题行
			createTitleRow(sheet, fieldInfos);
			// 设置每行的列宽
			initColumnsWidth(sheet, fieldInfos);
			// 循环插入剩下的集合
			for (int i = 0; i < dataList.size(); i++) {// 从第二行开始写，第一行是标题
				createContentRow(sheet, fieldInfos, (i+1), dataList.get(i));
			}
			workbook.write(out);
		} catch (Exception e) {
			log.error("Excel导出失败：", e);
			throw new RuntimeException("Excel导出失败：", e);
		} finally {
			if (workbook != null) {
				try {
					workbook.close();
				} catch (IOException e) {
					log.error("close excel workboot error: ", e);
				}
			}
		}
	}

	// 首先检查数据看是否是正确的
	private static void checkDataValid(String title, List<?> dataSet, OutputStream out) {
		if (dataSet == null || dataSet.size() == 0) {
			throw new RuntimeException("导出数据为空！");
		}
		if (title == null || out == null ) {
			throw new RuntimeException("传入参数不能为空！");
		}
	}

	/**
	 * 解析excel导入Field信息
	 * @param voClass  待到处vo对象class
	 * @return  
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 */
	private static List<ExportFieldInfo> parseExportFieldInfo(Class<?> dataClass)
			throws NoSuchMethodException, SecurityException {
		List<ExportFieldInfo> fieldInfos = new ArrayList<ExportFieldInfo>();
		// 得到所有字段
		Field[] fields = dataClass.getDeclaredFields();
		// 遍历整个filed
		for (Field field : fields) {
			ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
			// 如果设置了annotation
			if (excelConfig != null) {
				ExportFieldInfo info = new ExportFieldInfo();
				String title = excelConfig.exportName();// 添加到标题
				info.setTitle(title);
				int width = excelConfig.exportFieldWidth();// 添加标题的列宽
				info.setWidth(width);
				String fieldName = field.getName();// 添加到需要导出的字段的方法
				log.debug(title + " " + "列宽" + width);
				String getterMethodName = getGetterMethodNameByFieldName(fieldName);
				Method getterMethod = dataClass.getMethod(getterMethodName, new Class[] {});
				info.setGetterMethod(getterMethod);
				if (excelConfig.exportConvertSign()) {
					String convertMethodName = getConvertMethodNameByFieldName(fieldName);
					log.debug("convert method name : " + convertMethodName);
					Method convertMethod = dataClass.getMethod(convertMethodName, new Class[] {});
					info.setConvertMethod(convertMethod);
				}
				fieldInfos.add(info);
			}
		}
		return fieldInfos;
	}

	//通过字段名获取get方法名称 : getXXX
	private static String getGetterMethodNameByFieldName(String fieldName) {
		StringBuffer getterMethodName = new StringBuffer("get");
		getterMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
		return getterMethodName.toString();
	}

	//通过字段名获取conver方法名称: getXXXConvert
	private static String getConvertMethodNameByFieldName(String fieldName) {
		StringBuilder getConvertMethodName = new StringBuilder("get");
		getConvertMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1))
				.append("Convert");
		return getConvertMethodName.toString();
	}

	// 产生表格标题
	private static Row createTitleRow(Sheet sheet, List<ExportFieldInfo> fieldInfos) {
		Row row = sheet.createRow(0);// 产生表格标题行
		for (int i = 0; i < fieldInfos.size(); i++) {
			Cell cell = row.createCell(i);
			ExportFieldInfo info = fieldInfos.get(i);
			RichTextString text = new HSSFRichTextString(info.getTitle());
			cell.setCellValue(text);
		}
		return row;
	}

	// 设置每行宽度
	private static void initColumnsWidth(Sheet sheet, List<ExportFieldInfo> fieldInfos) {
		for (int i = 0; i < fieldInfos.size(); i++) {
			ExportFieldInfo info = fieldInfos.get(i);
			// 256=65280/255
			sheet.setColumnWidth(i, 256 * info.getWidth());
		}
	}

	// 创建excel中内容行的一行
	private static void createContentRow(Sheet sheet,List<ExportFieldInfo> fieldInfos, 
			int rowIndex, Object dataObject){
		// 创建excel中的一行
		Row row = sheet.createRow(rowIndex);
		try {
			for (int i = 0; i < fieldInfos.size(); i++) {
				ExportFieldInfo info = fieldInfos.get(i);
				// 填充行中的每一个单元格
				Cell cell = row.createCell(i);
				Object value = null;
				if (info.getConvertMethod() != null) {
					value = info.getConvertMethod().invoke(dataObject, new Object[] {});
				} else {
					value = info.getGetterMethod().invoke(dataObject, new Object[] {});
				}
				cell.setCellValue(value == null ? "" : value.toString());
			}
		} catch (Exception e) {
			throw new RuntimeException("创建excel第["+rowIndex+"]行出错!",e) ;
		}
	}
}
