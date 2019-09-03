package com.yicj.study.excel.exp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Excel导出配置
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExportConf {
	/**
	 * 索引:第几列
	 * @return
	 */
	public int index();
	/**
	 * 导入时，对应数据库的字段 主要是用户区分每个字段，不能有annotation重名的 导出时的列名 导出排序跟定义了annotation的字段的顺序有关
	 * @return 列名
	 */
	public String title();
	/**
	 * 导出时在excel中每个列的宽 单位为字符，一个汉字=2个字符 如以列名列内容中较合适的长度 例如姓名列6 【姓名一般三个字】
	 * 性别列4【男女占1，但是列标题两个汉字】 限制1-255
	 * @return 列宽
	 */
	public int width() default 20;
	/**
	  * 导出时是否进行字段转换 例如 性别用int存储，导出时可能转换为男，女 若是sign为true,则需要在pojo中加入一个方法
	 * get字段名Convert() 例如，字段sex ，需要加入 public String getSexConvert() 返回值为string
	  * 若是sign为false,则不必管
	 * @return 导出是否字段转换
	 */
	public boolean isConvert() default false;
}
