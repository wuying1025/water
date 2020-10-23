package com.water.constant;

public class CookieConstant {

	// cookie保持名称
	public static String LOGIN_SIGN = "Admin-Token";
	
	// redis,JWT过期时间30天,30天的毫秒数为2592000000L
	public static final long EXPIRE_TIME = 2592000000L;
	// cookie过期时间30天,30天的秒数为2592000
	public static final int EXPIRE_TIME_SECOND = 2592000;
}
