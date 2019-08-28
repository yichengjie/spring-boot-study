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


# 创建order表
	CREATE TABLE tb_order (
		id int(11) NOT NULL AUTO_INCREMENT,
		user_id int(11) DEFAULT NULL,
		order_number varchar(255) DEFAULT NULL,
		created datetime DEFAULT NULL,
		updated datetime DEFAULT NULL,
		PRIMARY KEY (id)
	) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE tb_item (
	id int(11) NOT NULL,
	item_name varchar(255) DEFAULT NULL,
	item_price decimal(10,2) DEFAULT NULL,
	item_detail varchar(255) DEFAULT NULL,
	PRIMARY KEY (id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# 插入数据
	INSERT INTO tb_item VALUES ('1', '袜子', '29.90', '香香的袜子');
	INSERT INTO tb_item VALUES ('2', '测试', '99.99', '测试');
	

CREATE TABLE tb_orderdetail (
	id int(11) NOT NULL AUTO_INCREMENT,
	order_id int(11) DEFAULT NULL,
	total_price decimal(10,0) DEFAULT NULL,
	item_id int(11) DEFAULT NULL,
	status int(10) unsigned zerofill DEFAULT NULL COMMENT '0成功非0失败',
	PRIMARY KEY (id)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;

INSERT INTO tb_orderdetail VALUES ('1', '1', '10000', '1', '0000000001');
INSERT INTO tb_orderdetail VALUES ('2', '1', '2000', '2', '0000000000');



	