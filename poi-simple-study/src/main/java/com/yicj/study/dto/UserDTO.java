package com.yicj.study.dto;

import com.yicj.study.excel.ExportConf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	private Integer id;
	@ExportConf(title = "姓名")
	private String name;
	@ExportConf(title = "性别", isConvert = true)
	private Integer sex;
	@ExportConf(title = "学科")
	private String subject;
	@ExportConf(title = "分数")
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
