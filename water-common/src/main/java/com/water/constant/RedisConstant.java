package com.water.constant;

/***
 * 
 * @ClassName:  RedisConstant   
 * @Description: redis 相关常量   
 * @author: zhangkaiqiang
 * @date:   Apr 1, 2020 10:52:10 PM
 */
public class RedisConstant {

	/**
	 * 管理端，登录用户保存状态的前缀
	 */
	public static String PREFIX_ADMIN = "admin:";
	
	
	/**
	 * 临时图片验证码图片
	 */
	public static String PREFIX_IMAGE_CODE = "vcode:";

	/**
	 * access_token超期时间
	 */
	public static long EXPIRE_IN = 7190;

}
