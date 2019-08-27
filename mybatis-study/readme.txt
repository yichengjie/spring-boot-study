# log4j配置
	log4j.rootLogger=DEBUG,A1
	log4j.logger.org.apache=DEBUG
	log4j.appender.A1=org.apache.log4j.ConsoleAppender
	log4j.appender.A1.layout=org.apache.log4j.PatternLayout
	log4j.appender.A1.layout.ConversionPattern=%-d{yyyy-MM-dd HH:mm:ss,SSS} [%t] [%c]-[%p] %m%n

#创建数据库
   CREATE DATABASE ssmdemo;

# 数据库建表
	DROP TABLE IF EXISTS tb_user;
	CREATE TABLE tb_user (
		id int NOT NULL auto_increment,
		user_name varchar(32) DEFAULT NULL,
		password varchar(32) DEFAULT NULL,
		name varchar(32) DEFAULT NULL,
		age int(10) DEFAULT NULL,
		sex int(2) DEFAULT NULL,
		birthday date DEFAULT NULL,
		created datetime DEFAULT NULL,
		updated datetime DEFAULT NULL,
		PRIMARY KEY (id)
	) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 插入测试数据
	INSERT INTO ssmdemo.tb_user (user_name, password, name, age, sex, birthday, created, updated) VALUES ('zpc', '123456', '鹏程', '22', '1', '1990-09-02', sysdate(), sysdate());
	INSERT INTO ssmdemo.tb_user (user_name, password, name, age, sex, birthday, created, updated) VALUES ('hj', '123456', '静静', '22', '1', '1993-09-05', sysdate(), sysdate());
