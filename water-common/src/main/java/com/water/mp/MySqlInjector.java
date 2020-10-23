package com.water.mp;

import java.util.List;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;
import com.baomidou.mybatisplus.extension.injector.methods.additional.LogicDeleteByIdWithFill;
/***
 * 
 * @ClassName:  MySqlInjector   
 * @Description:重写Mybatis-plus的sql注入器   
 * @author: 张凯强
 * @date:   Mar 2, 2020 6:42:49 PM
 */
public class MySqlInjector extends DefaultSqlInjector {

	@Override
	public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
		List<AbstractMethod> methodList = super.getMethodList(mapperClass);
		//添加删除填充方法
		methodList.add(new LogicDeleteByIdWithFill());
		return methodList;
	}
}
