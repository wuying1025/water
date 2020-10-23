package com.water.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.water.mp.MySqlInjector;
import com.baomidou.mybatisplus.autoconfigure.ConfigurationCustomizer;
import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.extension.MybatisMapWrapperFactory;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;

@Configuration
public class MybatisPlusConfiguration {

	/**
	 * mybatis-plus分页插件
	 * @return PaginationInterceptor
	 */
	@Bean
	public PaginationInterceptor paginationInterceptor() {
		PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

		//分页条数不受限制
		paginationInterceptor.setLimit(-1L);
//     取消阻断器，某些情况下报错
//		List<ISqlParser> sqlParserList = new ArrayList<>();
//		//增加攻击 SQL 阻断解析器，阻止恶意的全表更新删除
//		sqlParserList.add(new BlockAttackSqlParser());
//		paginationInterceptor.setSqlParserList(sqlParserList);
		
		return paginationInterceptor;
	}
	
	/**
	 * SQL注入器
	 * @return
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		
		return new MySqlInjector();
	}
	
	/**
	 * Map字段下划线转驼峰
	 * @return
	 */
	@Bean
	public ConfigurationCustomizer configurationCustomizer() {
	    return i -> i.setObjectWrapperFactory(new MybatisMapWrapperFactory());
	}
}
