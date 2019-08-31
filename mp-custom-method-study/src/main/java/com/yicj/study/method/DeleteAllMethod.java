package com.yicj.study.method;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.metadata.TableInfo;


public class DeleteAllMethod extends AbstractMethod {

	@Override
	public MappedStatement injectMappedStatement(Class<?> mapperClass, Class<?> modelClass, TableInfo tableInfo) {
		String sql = "delete from " + tableInfo.getTableName() ;
		String methodName = "deleteAll" ;
		SqlSource sqlSource = languageDriver.createSqlSource(configuration, sql, modelClass);
		return addDeleteMappedStatement(mapperClass, methodName, sqlSource);
	}

}
