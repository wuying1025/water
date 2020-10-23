package com.water.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/***
 * 
 * @ClassName:  CookieUtil   
 * @Description:工具类 
 * @author: zhangkaiqiang
 * @date:   Apr 1, 2020 10:41:58 PM
 */
public class CookieUtil {
	
	/**
	 * 
	 * @param myName 存在cookie中的名字
	 * @param req request对象
	 * @return 返回的cookie值，如果为null，代表cookie不存在
	 */
	public static String getCookieValue(String myName,HttpServletRequest req) {
		String value = null;
		
		Cookie[] cookies = req.getCookies();
		if(cookies != null) {
			for(Cookie cookie: cookies) {
				String name = cookie.getName();
				if(name.equals(myName)) {
					value = cookie.getValue();
				}
			}
		}else {
			value = req.getHeader("Authorization");
		}
		
		return value;
	}
}
