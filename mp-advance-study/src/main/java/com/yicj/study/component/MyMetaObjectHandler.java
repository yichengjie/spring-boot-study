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
		boolean hasCreateTime = metaObject.hasSetter("createTime") ;
		//自动填充优化：存在属性时才会自动填充值
		if(hasCreateTime) {
			setInsertFieldValByName("createTime", LocalDateTime.now(), metaObject) ;
		}
	}

	@Override
	public void updateFill(MetaObject metaObject) {
		log.info("updateFill updateTime ...");
		//自动填充优化：没有值才进行自动填充
		Object updateTime = getFieldValByName("updateTime", metaObject);
		if(updateTime == null) {
			setUpdateFieldValByName("updateTime2", LocalDateTime.now(), metaObject) ;
		}
	}

}
