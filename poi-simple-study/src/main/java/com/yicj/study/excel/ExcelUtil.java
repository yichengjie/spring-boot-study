package com.yicj.study.excel;

import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
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
	
	private static FieldsInfo parseFieldsInfo(Class<?> pojoClass, Collection<?> dataSet) throws NoSuchMethodException, SecurityException {
		FieldsInfo fieldsInfo = new FieldsInfo() ;
		// 标题
		List<String> exportFieldTitle = fieldsInfo.getTitleList() ;
		List<Integer> exportFieldWidth = fieldsInfo.getWidthList() ;
		// 拿到所有列名，以及导出的字段的get方法
		List<Method> methodList = fieldsInfo.getMethodList() ;
		Map<String, Method> convertMethod =  fieldsInfo.getConvertMethodMap() ;
		// 得到所有字段
		Field[] fields = pojoClass.getDeclaredFields();
		fieldsInfo.setFields(fields);
		// 是否求和配置
		boolean isSum = false;
		List<BigDecimal> sumList = fieldsInfo.getSumList() ;
		List<Boolean> isSumList = fieldsInfo.getIsSumList() ;
		List<Integer> scaleList = fieldsInfo.getScaleList() ;
		List<Boolean> isMergeList = fieldsInfo.getIsMergeList() ;
		List<Method> mergeFlagList = fieldsInfo.getMergeFlagList() ;
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
				// 记录是否求和配置
				if (i != 0) {
					if (excelConfig.isSum()) {
						isSum = true;
						log.debug(field.getName() + "需要合计");
						isSumList.add(true);
						sumList.add(new BigDecimal(0));
						scaleList.add(excelConfig.scale());
					} else {
						isSumList.add(false);
						sumList.add(null);
						scaleList.add(null);
					}
				} else {
					isSumList.add(false);
					sumList.add(null);
					scaleList.add(null);
				}
				// 是否合并
				isMergeList.add(excelConfig.isMerge());
				if (excelConfig.isMerge()) {
					StringBuilder getMergeFlagName = new StringBuilder("get");
					String mergeFlag;
					if (StringUtils.isBlank(excelConfig.mergeFlag())) {
						mergeFlag = getMethodName.toString();
					} else {
						getMergeFlagName.append(excelConfig.mergeFlag().substring(0, 1).toUpperCase())
								.append(excelConfig.mergeFlag().substring(1));
						mergeFlag = getMergeFlagName.toString();
					}
					Method getMergeFlag = pojoClass.getMethod(mergeFlag, new Class[] {});
					mergeFlagList.add(getMergeFlag);
				} else {
					mergeFlagList.add(null);
				}
			}
		}
		fieldsInfo.setSum(isSum);
		return fieldsInfo ;
	}
	
	//产生表格标题
	private static Row createExcelTitle(FieldsInfo fieldsInfo ,Sheet sheet) {
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
	private static void initColumnsWidth(FieldsInfo fieldsInfo,Sheet sheet) {
		List<Integer> widthList = fieldsInfo.getWidthList();
		for (int i = 0; i < widthList.size(); i++) {
			// 256=65280/255
			sheet.setColumnWidth(i, 256 * widthList.get(i));
		}
	}
	
	private static void initCellValue(Sheet sheet,Cell cell,CellStyle cellStyle, 
			int rowIndex, int columnIndex, FieldsInfo fieldsInfo,
			Object obj ,Collection<?> dataSet,Map<String, PoiModel> poiModelMap)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		List<Method> methodList = fieldsInfo.getMethodList();
		Map<String, Method> convertMethodMap = fieldsInfo.getConvertMethodMap();
		List<Boolean> isSumList = fieldsInfo.getIsSumList();
		List<BigDecimal> sumList = fieldsInfo.getSumList();
		List<Boolean> isMergeList = fieldsInfo.getIsMergeList();
		List<Method> mergeFlagList = fieldsInfo.getMergeFlagList();
		
		Method getMethod = methodList.get(columnIndex);
		Object value;
		if (convertMethodMap.containsKey(getMethod.getName())) {
			Method cm = convertMethodMap.get(getMethod.getName());
			value = cm.invoke(obj, new Object[] {});
		} else {
			value = getMethod.invoke(obj, new Object[] {});
		}
		cell.setCellValue(value == null ? "" : value.toString());
		// 合计计算操作
		if (isSumList.get(columnIndex)) {
			BigDecimal tempNum = sumList.get(columnIndex);
			if (value instanceof Number) {
				sumList.set(columnIndex, tempNum.add(new BigDecimal(value.toString())));
			} else if (value instanceof String) {
				sumList.set(columnIndex, tempNum.add(new BigDecimal(1)));
			} else {
				log.warn("未知合计类型" + value.toString());
				sumList.set(columnIndex, tempNum.add(new BigDecimal(1)));
			}
		}
		// 合并列
		if (isMergeList.get(columnIndex)) {
			String mergeValue;
			Method cm = mergeFlagList.get(columnIndex);
			mergeValue = cm.invoke(obj, new Object[] {}).toString();
			PoiModel poiModel = poiModelMap.get(getMethod.getName());
			if (poiModel == null) {
				poiModel = new PoiModel();
				poiModel.setRowIndex(rowIndex);
				poiModel.setContent(mergeValue);
				poiModelMap.put(getMethod.getName(), poiModel);
			} else {
				// 判断值是否相等，不相等则合并
				if (!poiModel.getContent().equals(mergeValue)) {
					// 合并单元格必须是2个或以上
					if (poiModel.getRowIndex() != (rowIndex - 1)) {
						CellRangeAddress cra = new CellRangeAddress(poiModel.getRowIndex(), 
								rowIndex - 1, columnIndex,columnIndex);
						sheet.addMergedRegion(cra);
						sheet.getRow(poiModel.getRowIndex()).getCell(columnIndex).setCellStyle(cellStyle);
					}
					poiModel.setContent(mergeValue);
					poiModel.setRowIndex(rowIndex);
					poiModelMap.put(getMethod.getName(), poiModel);
				} else {
					// 最后一行无法在进行比较，直接合并
					if (rowIndex == dataSet.size()) {
						if (poiModel.getRowIndex() != rowIndex) {
							CellRangeAddress cra = new CellRangeAddress(poiModel.getRowIndex(), 
									rowIndex, columnIndex,columnIndex);
							sheet.addMergedRegion(cra);
							sheet.getRow(poiModel.getRowIndex())
							.getCell(columnIndex).setCellStyle(cellStyle);
						}
					}
				}
			}
		}
		
	}
	
	/**
	 * 生成导出Excel
	 * @param title
	 * @param pojoClass
	 * @param dataSet
	 * @param out
	 */
	public static void exportExcel(String title, Class<?> pojoClass, 
			Collection<?> dataSet, 
			OutputStream out) {
		try {
			//首先检查数据看是否是正确的
			checkDataValid(title, pojoClass, dataSet, out);
			// 声明一个工作薄
			Workbook workbook = new HSSFWorkbook();
			// 生成一个表格
			Sheet sheet = workbook.createSheet(title);
			FieldsInfo fieldsInfo = parseFieldsInfo(pojoClass, dataSet);
			//产生表格标题行
			Row row = createExcelTitle(fieldsInfo, sheet) ;
			// 设置每行的列宽
			initColumnsWidth(fieldsInfo, sheet);
			// 设置单元格样色
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setVerticalAlignment(VerticalAlignment.CENTER) ;
			// 循环插入剩下的集合
			List<Method> methodList = fieldsInfo.getMethodList();
			List<Boolean> isSumList = fieldsInfo.getIsSumList();
			List<BigDecimal> sumList = fieldsInfo.getSumList();
			boolean isSum = fieldsInfo.isSum();
			List<Integer> scaleList = fieldsInfo.getScaleList();
			int rowIndex = 0;
			HashMap<String, PoiModel> poiModelMap = new HashMap<>();
			for(Object obj: dataSet) {
				rowIndex++;
				// 从第二行开始写，第一行是标题
				row = sheet.createRow(rowIndex);
				for (int i = 0; i < methodList.size(); i++) {
					Cell cell = row.createCell(i);
					initCellValue(sheet, cell, cellStyle, rowIndex, i, 
							fieldsInfo, obj, dataSet, poiModelMap);
				}
			}
			// 合计行显示操作
			if (isSum) {
				row = sheet.createRow(++rowIndex);
				row.createCell(0).setCellValue("合计");
				for (int k = 0; k < isSumList.size(); k++) {
					if (isSumList.get(k)) {
						Cell cell = row.createCell(k);
						cell.setCellValue((sumList.get(k).setScale(scaleList.get(k), 
								RoundingMode.HALF_UP)).toString());
					}
				}
			}
			workbook.write(out);
		} catch (Exception e) {
			log.error("Excel导出失败：", e);
		}
	}

}
