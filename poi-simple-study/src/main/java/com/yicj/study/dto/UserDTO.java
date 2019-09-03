package com.yicj.study.dto;

import com.yicj.study.excel.exp.ExportConf;
import com.yicj.study.excel.imp.ImportConf;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserDTO {
	private Integer id;
	@ImportConf(index=1)
	@ExportConf(index=0, title = "姓名")
	private String name;
	@ImportConf(index=3,isConvert = true)
	@ExportConf(index =1, title = "性别", isConvert = true)
	private Integer sex;
	@ImportConf(index=4)
	@ExportConf(index =2, title = "学科")
	private String subject;
	@ImportConf(index=5)
	@ExportConf(index= 3, title = "分数")
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
	
	public void setSexConvert(String str) {
		switch (str) {
		case "未知":
			this.sex = 0 ;
			break ;
		case "男":
			this.sex = 1 ;
			break ;
		case "女":
			this.sex = 2;
			break;
		default:
			this.sex = 0;
			break;
		}
	}
}
