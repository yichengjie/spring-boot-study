package com.yicj.study.excel;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.apache.poi.ss.util.CellRangeAddress;

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
			// 设置合并单元格样色
			CellStyle mergeStyle = workbook.createCellStyle();
			mergeStyle.setVerticalAlignment(VerticalAlignment.CENTER) ;
			// 循环插入剩下的集合
			Map<String, MergeModel> mergeModelMap = new HashMap<String, MergeModel> (); 
			for (int i = 0; i < dataList.size(); i++) {// 从第二行开始写，第一行是标题
				createContentRow(sheet, fieldInfos, i, dataList,mergeModelMap,mergeStyle);
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
				info.setFieldName(fieldName);
				log.debug(title + " " + "列宽" + width);
				String getterMethodName = getGetterMethodNameByFieldName(fieldName);
				Method getterMethod = dataClass.getMethod(getterMethodName, new Class[] {});
				info.setGetterMethod(getterMethod);
				if (excelConfig.exportConvertFlag()) {
					String convertMethodName = getConvertMethodNameByFieldName(fieldName);
					log.debug("convert method name : " + convertMethodName);
					Method convertMethod = dataClass.getMethod(convertMethodName, new Class[] {});
					info.setConvertMethod(convertMethod);
				}
				String exportMergeKey =  dealNull(excelConfig.exportMergeKey());
				if(exportMergeKey.length() > 0) {
					String mergeKeyMethodName = getGetterMethodNameByFieldName(exportMergeKey) ;
					Method mergeKeyMethod = dataClass.getMethod(mergeKeyMethodName, new Class[] {});
					info.setMergeKeyMethod(mergeKeyMethod);
				}
				fieldInfos.add(info);
			}
		}
		return fieldInfos;
	}
	
	
	private static String dealNull(String str) {
		if(str ==null) {
			return "" ;
		}
		return str.trim() ;
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
			int index, List<?> dataList,Map<String, MergeModel> mergeModelMap, 
			CellStyle mergeStyle){
		// 创建excel中的一行
		Row row = sheet.createRow(index + 1);
		Object dataObject = dataList.get(index) ;
		try {
			//循环所有字段
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
				//判断是否需要合并单元格，如果需要合并单元格
				if(info.getMergeKeyMethod()!=null) {
					mergeRow(info, mergeModelMap, dataList, index, i, sheet, mergeStyle);
				}
			}
			//合并单元格
		} catch (Exception e) {
			throw new RuntimeException("创建excel第["+(index + 1)+"]行出错!",e) ;
		}
	}
	
	
	private static void mergeRow(ExportFieldInfo info, Map<String, MergeModel> mergeModelMap,
			List<?> dataList, int index, int columnIndex,Sheet sheet, CellStyle mergeStyle) 
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object dataObject = dataList.get(index) ;
		String fieldName = info.getFieldName() ;
		String mergeKeyValue = info.getMergeKeyMethod().invoke(dataObject, new Object[] {}).toString();
		MergeModel mergeModel = mergeModelMap.get(fieldName);
		int rowIndex = (index + 1) ;
		//1.如果mergeModel为空，则新建
		if (mergeModel == null) {
			mergeModel = new MergeModel();
			//当前是第几行
			mergeModel.setRowIndex(rowIndex);
			//合并列内容
			mergeModel.setContent(mergeKeyValue);
			//保存起来
			mergeModelMap.put(fieldName, mergeModel);
			return ;
		}
		//2.如果mergeModel不为空，则需要比较判断
		//2.1 如果不为空，且值不相等则合并
		if (!mergeModel.getContent().equals(mergeKeyValue)) {
			// 合并单元格必须是2个或以上
			if (mergeModel.getRowIndex() != (rowIndex - 1)) {
				CellRangeAddress cra = new CellRangeAddress(mergeModel.getRowIndex(), 
						rowIndex - 1, columnIndex,columnIndex);
				sheet.addMergedRegion(cra);
				sheet.getRow(mergeModel.getRowIndex()).getCell(columnIndex)
					 .setCellStyle(mergeStyle);
			}
			mergeModel.setContent(mergeKeyValue);
			mergeModel.setRowIndex(rowIndex);
			mergeModelMap.put(fieldName, mergeModel);
			return ;
		} 
		//2.2 如果不为空，且值相同，则判断是否为最后一行，如果为最后一行，且index不为当前index，则直接合并
		// 最后一行无法在进行比较，直接合并
		if (rowIndex == dataList.size()) {
			if (mergeModel.getRowIndex() != rowIndex) {
				CellRangeAddress cra = new CellRangeAddress(mergeModel.getRowIndex(), 
						rowIndex, columnIndex,columnIndex);
				sheet.addMergedRegion(cra);
				sheet.getRow(mergeModel.getRowIndex())
				.getCell(columnIndex).setCellStyle(mergeStyle);
			}
		}
		
	}
	
}
