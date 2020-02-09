package com.yicj.study.dto;

import com.yicj.study.excel.ExcelConfig;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	private Integer id;
	@ExcelConfig(exportName = "姓名", isMerge = true, mergeFlag = "id")
	private String name;
	@ExcelConfig(exportName = "性别", exportConvertSign = true)
	private Integer sex;
	@ExcelConfig(exportName = "学科")
	private String subject;
	@ExcelConfig(exportName = "分数")
	private Integer score;

	public String getSexConvert() {
		switch (sex) {
		case 0:
			return "未知";
		case 1:
			return "男";
		case 2:
			return "女";
		default:
			return "未知";
		}
	}

}
