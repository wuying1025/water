package com.water.base;

import java.io.Serializable;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.water.exception.BatchDelException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * 
 * @ClassName: R
 * @Description: 返回值
 * @author zhangkaiqiang
 * @date 2019年7月2日 下午4:04:44
 * 
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class R<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public R(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/**
	 * 结果码
	 */
	private String code;

	/**
	 * 消息内容
	 */
	private String msg;

	/**
	 * 数据域
	 */
	private T data;

	public R(ResultCode resultCode) {
		super();
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
		//设置响应状态码
		setResponseStatus(resultCode.getHttpCode());
	}

	private R(ResultCode resultCode, String msg) {
		super();
		this.code = resultCode.getCode();
		this.msg = StringUtils.isEmpty(msg) ? resultCode.getMsg() : msg;
		//设置响应状态码
		setResponseStatus(resultCode.getHttpCode());
	}

	public R(ResultCode resultCode, T data) {
		super();
		this.code = resultCode.getCode();
		this.msg = resultCode.getMsg();
		this.data = data;
		//设置响应状态码
		setResponseStatus(resultCode.getHttpCode());
	}

	/**
	 * 操作成功.
	 * 
	 * @return Result(200, "success", "操作成功")
	 */
	public static <T> R<T> success() {
		return new R<>(ResultCode.OK);
	}

	/**
	 * 操作成功
	 * @param msg 状态信息
	 * @return
	 */
	public static <T> R<T> success(String msg) {
		return new R<>(ResultCode.OK, msg);
	}

	/**
	 * 操作成功.
	 * 
	 * @param data 响应数据
	 * @return Result(200, "success", "操作成功", data)
	 */
	public static <T> R<T> success(T data) {
		return new R<T>(ResultCode.OK, data);
	}


	/**
	 * 操作失败.
	 * 
	 * @return
	 */
	public static <T> R<T> fail() {
		return new R<>(ResultCode.FAIL);
	}
	
	/**
	 * 操作失败.
	 * @param e 异常信息
	 * @return
	 */
	public static <T> R<T> fail(Exception e) {
		if(e instanceof BatchDelException) {
			return new R<>(ResultCode.RESOURCES_NOT_EXIST);
		}
		
		return new R<>(ResultCode.FAIL);
	}

	/**
	 * 操作失败
	 * @param msg 状态信息
	 * @return
	 */
	public static <T> R<T> fail(String msg) {
		return new R<>(ResultCode.FAIL, msg);
	}

	/**
	 * 操作失败.
	 * 
	 * @param data 响应数据
	 * @return
	 */
	public static <T> R<T> fail(T data) {
		return new R<>(ResultCode.FAIL, data);
	}

	/**
	 * 系统错误.
	 * 
	 * @return Result(500, "error", "操作失败，系统错误，请稍后重试！")
	 */
	public static <T> R<T> error() {
		return new R<>(ResultCode.ERROR);
	}
	
	/**
	 * 系统错误.
	 * @param e 系统错误
	 * @return
	 */
	public static <T> R<T> error(Exception e) {
		if(e instanceof BatchDelException) {
			return new R<>(ResultCode.RESOURCES_NOT_EXIST);
		}
		
		return new R<>(ResultCode.ERROR);
	}

	public static <T> R<T> result(ResultCode resultCode) {
		return new R<>(resultCode);
	}
	
	public static <T> R<T> result(ResultCode resultCode, T data) {
		return new R<>(resultCode, data);
	}

	public static <T> R<T> result(ResultCode resultCode, String msg) {
		return new R<>(resultCode, msg);
	}

	/**
	 * 对执行结果进行BaseResponse包装（模板方法）;
	 * @param supplier 执行函数
	 * @return 执行成功返回：BaseResponse.success(supplier执行结果)； 执行失败返回：BaseResponse.error();
	 */
	public static <T> R<T> wrap(Supplier<T> supplier) {
		try {
			return R.success(supplier.get());
		} catch (Exception e) {
			return R.error();
		}
	}

	public ResponseEntity<R<T>> sendResponse() {
		return ResponseEntity.status(ResultCode.getResultCode(this.code).get().getHttpCode()).body(this);
	}
	
	/**
	 * 设置响应状态码
	 * @param statusCode 响应状态码
	 */
	private void setResponseStatus(int statusCode) {
		HttpServletResponse response = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
		response.setStatus(statusCode);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	

}
