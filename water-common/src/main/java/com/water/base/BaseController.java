package com.water.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;

import io.swagger.annotations.ApiOperation;

public class BaseController<M extends MyMapper<T>, T> {
	@Autowired
	private MyService<M,T> service;
	
	@ApiOperation("通过id查询实体")
	@GetMapping("getById/{id}")
	public R<T> getById(@PathVariable("id") String id){
		if(StringUtils.isEmpty(id)) {
			return R.result(ResultCode.PATAM_REQUIRED_NOT_FILL);
		}
		try {
			T t = service.query().eq("ID", id).one();
			return R.success(t);
		}catch(Exception e) {
			e.printStackTrace();
			return R.fail("查询失败");
		}
	}
	
	@ApiOperation("通过id删除实体")
	@GetMapping("delById/{id}")
	public R<String> delById(@PathVariable("id") String id){
		if(StringUtils.isEmpty(id)) {
			return R.result(ResultCode.PATAM_REQUIRED_NOT_FILL);
		}
		try {
			service.deleteById(id);
			return R.success("删除成功");
		}catch(Exception e) {
			e.printStackTrace();
			return R.fail("删除失败");
		}
	}
	
	@ApiOperation("更新 通过id")
	@PostMapping("updById")
	public R<String> updById(@RequestBody T param){
		try {
			service.lambdaUpdate().update(param);
			return R.success("更新成功");
		}catch(Exception e) {
			e.printStackTrace();
			return R.fail("更新失败");
		}
	}
	
	

}
