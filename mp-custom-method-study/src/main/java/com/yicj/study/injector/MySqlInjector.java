package com.yicj.study.injector;

import java.util.List;

import org.springframework.stereotype.Component;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.additional.InsertBatchSomeColumn;
import com.yicj.study.method.DeleteAllMethod;

@Component
public class MySqlInjector  extends DefaultSqlInjector{

	@Override
	public List<AbstractMethod> getMethodList() {
		 //注意这里需要先获取原方法，然后再加入自定义的方法
		 List<AbstractMethod> methodList = super.getMethodList();
		 methodList.add(new DeleteAllMethod()) ;
		 methodList.add(new InsertBatchSomeColumn(p -> {
			 return !p.isLogicDelete() && 
					!p.getColumn().equals("version") && 
					!p.getColumn().equals("deleted");
		 })) ;
		 return methodList ;
	}
}
