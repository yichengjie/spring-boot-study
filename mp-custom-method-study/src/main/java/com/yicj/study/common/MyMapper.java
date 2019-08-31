package com.yicj.study.common;

import java.util.List;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

public  interface MyMapper <T> extends BaseMapper<T> {
	//删除所有
	int deleteAll() ;
	
	int insertBatchSomeColumn(List<T> list) ;
}
