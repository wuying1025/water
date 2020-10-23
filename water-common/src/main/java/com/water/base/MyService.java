package com.water.base;

import java.io.Serializable;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.water.exception.BatchDelException;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;

/***
 * 
 * @ClassName:  MyService   
 * @Description: 基础方法   
 * @author: 张凯强
 * @date:   Mar 3, 2020 9:33:16 AM     
 * @param <M>
 * @param <T>
 */
public class MyService<M extends MyMapper<T>, T> extends ServiceImpl<MyMapper<T>, T> {
	
	private Logger logger = LoggerFactory.getLogger(MyService.class);
	
	/**
	 * 通用新增方法
	 * @param entity 实体
	 * @return BaseResponse<Object> 返回信息
	 */
	@Transactional(rollbackFor = Exception.class)
	public R<Object> saveEntity(T entity) {
		
		R<Object> result = null;
		
		try {
			boolean retBool = save(entity);
			if(retBool) {
				result = R.success();
			} else {
				result = R.fail();
			}
		} catch (Exception e) {
			logger.error("新增异常：", e);
			result = R.error();
		}
		
		return result;
	}
	
	/**
	 * 通用修改方法
	 * @param entity 实体
	 * @return BaseResponse<Object> 返回信息
	 */
	public R<Object> update(T entity) {
		
		R<Object> result = null;
		
		try {
			boolean retBool = updateById(entity);
			if(retBool) {
				result = R.success();
			} else {
				result = R.result(ResultCode.RESOURCES_NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error("修改异常：", e);
			result = R.error();
		}
		
		return result;
	}
	
	/**
	 * 通用删除方法
	 * @param id 主键
	 * @return BaseResponse<Object> 返回信息
	 */
	public R<Object> deleteById(Serializable id) {
		
		R<Object> result = null;
		
		try {
			boolean retBool = removeById(id);
			if(retBool) {
				result = R.success();
			} else {
				result = R.result(ResultCode.RESOURCES_NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error("删除异常：", e);
			result = R.error();
		}
		
		return result;
	}
	
	/**
	 * 通用删除方法，逻辑删除自动填充
	 * @param entity 实体
	 * @return BaseResponse<Object> 返回信息
	 */
	public R<Object> deleteByIdWithFill(T entity) {
		
		R<Object> result = null;
		
		try {
			boolean retBool = SqlHelper.retBool(baseMapper.deleteByIdWithFill(entity));
			if(retBool) {
				result = R.success();
			} else {
				result = R.result(ResultCode.RESOURCES_NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error("删除异常：", e);
			result = R.error();
		}
		
		return result;
	}
	
	/**
	 * 通用批量删除方法
	 * @param idList 主键集合
	 * @return BaseResponse<Object> 返回信息
	 */
	public R<Object> delBatchByIds(Collection<? extends Serializable> idList) {
		
		R<Object> result = null;
		
		try {
			boolean retBool = removeByIds(idList);
			if(retBool) {
				result = R.success();
			} else {
				result = R.result(ResultCode.RESOURCES_NOT_EXIST);
			}
		} catch (Exception e) {
			logger.error("批量删除异常：", e);
			result = R.error();
		}
		
		return result;
	}
	
	/**
	 * 通用批量删除方法，逻辑删除自动填充
	 * @param idList 主键集合
	 * @return BaseResponse<Object> 返回信息
	 * @throws Exception 
	 */
	@Transactional(rollbackFor = Exception.class)
	public R<Object> delBatchByIdsWithFill(Collection<T> idList) throws Exception {
		
		for(T entity:idList) {
			boolean retBool = SqlHelper.retBool(baseMapper.deleteByIdWithFill(entity));
			
			if(!retBool) {
				throw new BatchDelException("批量删除失败");
			}
		}
		return R.success();
	}
}