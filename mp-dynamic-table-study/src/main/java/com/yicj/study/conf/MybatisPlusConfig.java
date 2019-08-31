package com.yicj.study.conf;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
import com.baomidou.mybatisplus.extension.parsers.DynamicTableNameParser;
import com.baomidou.mybatisplus.extension.parsers.ITableNameHandler;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantHandler;
import com.baomidou.mybatisplus.extension.plugins.tenant.TenantSqlParser;
import com.yicj.study.common.GlobalSession;
import com.yicj.study.vo.UserVo;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.StringValue;

@Configuration
public class MybatisPlusConfig {

	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();
		/*
		 * 【测试多租户】 SQL 解析处理拦截器<br> 这里固定写成住户 1 实际情况你可以从cookie读取，因此数据看不到 【 麻花藤 】 这条记录（
		 * 注意观察 SQL ）<br>
		 */
		List<ISqlParser> sqlParserList = new ArrayList<>();
		//动态表名处理器
		Map<String, ITableNameHandler> tableNameHandlerMap = new HashMap<String, ITableNameHandler>();
		tableNameHandlerMap.put("user", new MyITableNameHandler()) ;
		DynamicTableNameParser tableNamewParser = new DynamicTableNameParser();
		tableNamewParser.setTableNameHandlerMap(tableNameHandlerMap) ;
		//将动态表名处理器加入到分页拦截器中
		sqlParserList.add(tableNamewParser);
		paginationInterceptor.setSqlParserList(sqlParserList);
		//多租户过滤器
		paginationInterceptor.setSqlParserFilter(new MyISqlParserFilter());
		return paginationInterceptor;
	}
	
	class MyITableNameHandler implements ITableNameHandler{
		//返回替换后的表名称
		@Override
		public String dynamicTableName(MetaObject metaObject, String sql, String tableName) {
			UserVo vo = GlobalSession.getUserVo();
			return tableName+"_" + vo.getTenantId();
		}
		
	}
	
	//过滤特定的sql
	class MyISqlParserFilter implements ISqlParserFilter{
		@Override
		public boolean doFilter(MetaObject metaObject) {
			MappedStatement ms = SqlParserHelper.getMappedStatement(metaObject);
			// 过滤自定义查询此时无租户信息约束【 麻花藤 】出现
			if ("com.yicj.study.user.Usermapper.selectListBySQL".equals(ms.getId())) {
				return true;
			}
			return false;
		}
	}

	

}
