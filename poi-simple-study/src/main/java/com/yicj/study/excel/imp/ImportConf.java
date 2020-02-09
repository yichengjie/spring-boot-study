package com.yicj.study.excel.imp;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ImportConf {
	/**
	 * 索引:第几列
	 * @return
	 */
	public int index() ;
	/**
	 * 导入时是否需要字段值转换,例如性别字段：1：男，2：女
	 * excel存： 男，女
	 * 数据库存：1,2
	 */
	public boolean isConvert() default false;
}
