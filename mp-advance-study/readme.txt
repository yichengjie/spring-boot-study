1.在mp-generator-study项目的基础上练习mp的高级功能

2.性能分析插件p6spy
  2.1 maven添加p6spy依赖
  2.2 datasource:
  		driver-class-name: com.p6spy.engine.spy.P6SpyDriver
		url: jdbc:p6spy:h2:mem:test
  2.3 spy.properties 配置：

3.乐观锁
  3.1 增加OptimisticLockerInterceptor的bean
      @Bean
	  public OptimisticLockerInterceptor optimisticLockerInterceptor() {
	     return new OptimisticLockerInterceptor();
	  }
  3.2 注解实体字段 @Version 必须要!
      @Version
	  private Integer version;
	  
	  
4. 自定义wrapper
   4.1 XXXMapper.java文件中增加接口
       @Select("SELECT * FROM user ${ew.customSqlSegment}")
       List<User> selectByMyWrapper(@Param(Constants.WRAPPER) Wrapper<User> userWrapper);
   4.2 XXXMapper.xml文件中增加sql
       <select id="selectByMyWrapper" resultType="User">
	       SELECT * FROM user ${ew.customSqlSegment}
	   </select>
   4.3 XXXService层中增加接口，并注入XXXMapper
       @Autowired
	   private UserMapper userMapper ;
   4.3 单元测试即可
       