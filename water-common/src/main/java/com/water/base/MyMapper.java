package com.water.base;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 
 * @author zhangkaiqiang
 *
 * @param <T> 泛型为操作的实体
 */
public interface MyMapper<T> extends BaseMapper<T> {

	/**
	 * 根据 id 逻辑删除数据,并带字段填充功能
	 * @param entity 实体对象
	 * @return 影响行数
	 */
	int deleteByIdWithFill(T entity);
}
