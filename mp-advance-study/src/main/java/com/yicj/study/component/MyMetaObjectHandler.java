package com.yicj.study.component;

import java.time.LocalDateTime;

import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

	@Override
	public void insertFill(MetaObject metaObject) {
		log.info("insertFill createTime ...");
		setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject) ;
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("updateFill updateTime ...");
		setUpdateFieldValByName("updateTime", LocalDateTime.now(), metaObject) ;
	}

}
