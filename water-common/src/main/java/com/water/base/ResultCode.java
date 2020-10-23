package com.water.base;

import java.util.Optional;
import java.util.stream.Stream;

/***
 * 
 * @ClassName:  ResultCode   
 * @Description: 返回码   
 * @author: 张凯强
 * @date:   Feb 1, 2020 7:00:23 AM
 */
public enum ResultCode {

	/**
	 * 10000 到50000 以内位 业务正常 ,》=50000 异常
	 */
	OK("200", "成功", 200),
	FAIL("50000", "失败", 500),
	ERROR("50001", "错误", 500),
	USER_DISABLE("50004", "用户被停用", 401),
	UNAUTHORIZED("50005", "没有权限", 401), 
	USER_TYPE_ERROR("50006", "用户身份错误", 403),
	/***
	 * 用户不存在
	 */
	USER_NOT_EXIST("50007","用户不存在", 403),
	/***
	 * 密码错误
	 */
	USER_PASSWD_ERROR("50008","用户密码错误",403),
	/***
	 * 必填参数为空
	 */
	PATAM_REQUIRED_NOT_FILL("60001","必填参数为空",403),
	
	ERROR_TOKEN("50002", "TOKEN失败", 401), 

	USERNAME_EMPTY("2036","用户名为空！", 403),
	
	PASSWORD_EMPTY("2037","密码为空！", 403),
	
	USER_EXIST("2038","用户已存在！", 403),
	
	USER_PHONE_EXIST("2040","当前手机号已经被注册!",403),
	
	USER_OPEN_ID_FAIL("2039","用户openId获取失败", 403),

	ERROR_USER("50008", "登录失败，用户名或密码错误", 403),
	RESOURCES_NOT_EXIST("55000","操作的资源不存在",404),
	OK_CHUNK("201", "分片上传中", 200);
	private String code;

	private String msg;

	private int httpCode;

	ResultCode(String code, String msg, int httpCode) {
		this.code = code;
		this.msg = msg;
		this.httpCode = httpCode;
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

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

	public static Optional<ResultCode> getResultCode(String code) {
		return Stream.of(ResultCode.values()).filter(s -> code.equals(s.getCode())).findFirst();
	}
}
