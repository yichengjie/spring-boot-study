package com.yicj.study.conf;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import com.baomidou.mybatisplus.core.parser.SqlParserHelper;
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
		//多租户处理器
		TenantSqlParser tenantSqlParser = new TenantSqlParser();
		tenantSqlParser.setTenantHandler(new MyTenantHandler());
		sqlParserList.add(tenantSqlParser);
		paginationInterceptor.setSqlParserList(sqlParserList);
		//多租户过滤器
		paginationInterceptor.setSqlParserFilter(new MyISqlParserFilter());
		return paginationInterceptor;
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

	//多租户处理器
	class MyTenantHandler implements TenantHandler {
		@Override
		// 该 where 条件 3.2.0 版本开始添加的，用于分区是否为在 where 条件中使用
		public Expression getTenantId() {
			// 此判断用于支持返回多个租户 ID 场景，具体使用查看示例工程
			//获取到当前登录的用户信息
			UserVo userVo = GlobalSession.getUserVo() ;
			if(userVo ==null || userVo.getTenantId() == null) {
				throw new RuntimeException("用户未登录!") ;
			}
			return new StringValue(userVo.getTenantId());
		}
		@Override
		public String getTenantIdColumn() {
			//注意是数据库中的字段名，不是entity字段名
			return "tenant_id";
		}
		@Override
		public boolean doTableFilter(String tableName) {
			// 这里可以判断是否过滤表
			/*
			 * if ("user".equals(tableName)) { return true; }
			 */
			//false：加租户信息，true：不加租房信息
			return false;
		}
	}

}
