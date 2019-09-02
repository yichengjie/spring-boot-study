package com.yicj.study.excel;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ExcelUtil {

	// 首先检查数据看是否是正确的
	private static void checkDataValid(String title, Class<?> pojoClass, 
				Collection<?> dataSet, OutputStream out) {
		if (dataSet == null || dataSet.size() == 0) {
			throw new RuntimeException("导出数据为空！");
		}
		if (title == null || out == null || pojoClass == null) {
			throw new RuntimeException("传入参数不能为空！");
		}
	}
	
	private static ExportFieldsInfo parseFieldsInfo(Class<?> pojoClass, Collection<?> dataSet) throws NoSuchMethodException, SecurityException {
		ExportFieldsInfo fieldsInfo = new ExportFieldsInfo() ;
		// 标题
		List<String> exportFieldTitle = fieldsInfo.getTitleList() ;
		List<Integer> exportFieldWidth = fieldsInfo.getWidthList() ;
		// 拿到所有列名，以及导出的字段的get方法
		List<Method> methodList = fieldsInfo.getMethodList() ;
		Map<String, Method> convertMethod =  fieldsInfo.getConvertMethodMap() ;
		// 得到所有字段
		Field[] fields = pojoClass.getDeclaredFields();
		fieldsInfo.setFields(fields);
		// 遍历整个filed
		for (int i = 0; i < fields.length; i++) {
			Field field = fields[i];
			ExcelConfig excelConfig = field.getAnnotation(ExcelConfig.class);
			// 如果设置了annotation
			if (excelConfig != null) {
				// 添加到标题
				exportFieldTitle.add(excelConfig.exportName());
				// 添加标题的列宽
				exportFieldWidth.add(excelConfig.exportFieldWidth());
				// 添加到需要导出的字段的方法
				String fieldName = field.getName();
				log.debug(i + excelConfig.exportName() + " " + "列宽" + excelConfig.exportFieldWidth());
				StringBuffer getMethodName = new StringBuffer("get");
				getMethodName.append(fieldName.substring(0, 1).toUpperCase()).append(fieldName.substring(1));
				Method getMethod = pojoClass.getMethod(getMethodName.toString(), new Class[] {});
				methodList.add(getMethod);

				if (excelConfig.exportConvertSign()) {
					StringBuilder getConvertMethodName = new StringBuilder("get");
					getConvertMethodName.append(fieldName.substring(0, 1).toUpperCase())
							.append(fieldName.substring(1)).append("Convert");
					log.debug("convert: " + getConvertMethodName.toString());
					Method getConvertMethod = pojoClass.getMethod(getConvertMethodName.toString(), new Class[] {});
					convertMethod.put(getMethodName.toString(), getConvertMethod);
				}
			}
		}
		return fieldsInfo ;
	}
	
	//产生表格标题
	private static Row createExcelTitle(ExportFieldsInfo fieldsInfo ,Sheet sheet) {
		// 产生表格标题行
		List<String> titleList = fieldsInfo.getTitleList();
		Row row = sheet.createRow(0);
		for (int i = 0, size = titleList.size(); i < size; i++) {
			Cell cell = row.createCell(i);
			RichTextString text = new HSSFRichTextString(titleList.get(i));
			cell.setCellValue(text);
		}
		return row ;
	}
	//设置每行宽度
	private static void initColumnsWidth(ExportFieldsInfo fieldsInfo,Sheet sheet) {
		List<Integer> widthList = fieldsInfo.getWidthList();
		for (int i = 0; i < widthList.size(); i++) {
			// 256=65280/255
			sheet.setColumnWidth(i, 256 * widthList.get(i));
		}
	}
	
	private static void initCellValue(Cell cell, int columnIndex, ExportFieldsInfo fieldsInfo,Object obj)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Method> methodList = fieldsInfo.getMethodList();
		Map<String, Method> convertMethodMap = fieldsInfo.getConvertMethodMap();
		
		Method getMethod = methodList.get(columnIndex);
		Object value;
		if (convertMethodMap.containsKey(getMethod.getName())) {
			Method cm = convertMethodMap.get(getMethod.getName());
			value = cm.invoke(obj, new Object[] {});
		} else {
			value = getMethod.invoke(obj, new Object[] {});
		}
		cell.setCellValue(value == null ? "" : value.toString());
	}
	
	/**
	 * 生成导出Excel
	 * @param title
	 * @param pojoClass
	 * @param dataSet
	 * @param out
	 */
	public static void exportExcel(String title, Class<?> pojoClass, 
			Collection<?> dataSet, OutputStream out) {
		try {
			//首先检查数据看是否是正确的
			checkDataValid(title, pojoClass, dataSet, out);
			// 声明一个工作薄
			Workbook workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			ExportFieldsInfo fieldsInfo = parseFieldsInfo(pojoClass, dataSet);
			//产生表格标题行
			Row row = createExcelTitle(fieldsInfo, sheet) ;
			// 设置每行的列宽
			initColumnsWidth(fieldsInfo, sheet);
			// 设置单元格样色
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER) ;
			// 循环插入剩下的集合
			List<Method> methodList = fieldsInfo.getMethodList();
			int rowIndex = 1;
			for(Object obj: dataSet) {
				// 从第二行开始写，第一行是标题
				row = sheet.createRow(rowIndex);
				for (int columnIndex = 0; columnIndex < methodList.size(); columnIndex++) {
					Cell cell = row.createCell(columnIndex);
					initCellValue(cell, columnIndex, fieldsInfo, obj);
				}
				rowIndex++;
			}
			workbook.write(out);
		} catch (Exception e) {
			log.error("Excel导出失败：", e);
			throw new RuntimeException("Excel导出失败：", e) ;
		}
	}

}
