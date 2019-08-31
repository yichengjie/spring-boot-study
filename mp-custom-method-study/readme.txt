1. sql注入器-自定义方法
   1.1 创建自定义方法DeleteAllMethod.java继承extends AbstractMethod并实现injectMappedStatement方法
   1.2 创建sql注入器MySqlInjector继承DefaultSqlInjector类,重写getMethodList方法，加入自定义的方法
   1.3 在MySqlInjector上注解@Component交由spring管理
   1.4 在XXXMapper.java类中增加自定义方法签名即可.
       int deleteAll() ;
   1.5 单元测试deleteAll方法

		  
