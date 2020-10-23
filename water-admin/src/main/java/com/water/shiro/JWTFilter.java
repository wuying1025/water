package com.water.shiro;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.water.base.R;
import com.water.base.ResultCode;
import com.water.constant.CookieConstant;
import com.water.util.CookieUtil;
import com.water.util.JWTUtil;
import com.water.util.RedisUtil;
import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;

public class JWTFilter extends BasicHttpAuthenticationFilter {

	private Logger logger = LoggerFactory.getLogger(JWTFilter.class);
	
	@Autowired
	private RedisUtil redisUtil;
	
	/**
	 * 检测用户是否登录
	 * 检测header里面是否包含Authorization字段即可
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	@Override
	protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
		return false;
	
	}

	@Override
	protected boolean executeLogin(ServletRequest request, ServletResponse response) throws AuthenticationException {

		return false;
	}
	 public static void main(String[] args) {
		String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE1ODg3ODE1MzcsInVzZXJuYW1lIjoibG9naW5fdXNlcl9rZXk6Y2FiYjA1YTctY2E2OS00MmQxLTk1NTQtZGYzZjhmMTI0ZDAwIn0.cXefN6zQiLflN1ukxotDUpQBLRcn92lhRd88v2bYxUE";
		System.out.println(JWT.decode(token).getClaim("username").asString());
		
		
	}

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
		if (isLoginAttempt(request, response)) {
			boolean executeLogin = executeLogin(request, response);
			if(!executeLogin) {
				
				R<Object> result = R.result(ResultCode.ERROR_TOKEN);
				
				try {
					response.setContentType("application/json;charset=utf-8");
					response.getWriter().write(JSON.toJSONString(result, SerializerFeature.WriteNullStringAsEmpty));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
			return executeLogin;
		}

		return true;
	}

	/**
	 * 对跨域提供支持
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		HttpServletResponse httpServletResponse = (HttpServletResponse) response;
		httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
		httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
		httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
		// 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
		if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
			httpServletResponse.setStatus(HttpStatus.OK.value());
			return false;
		}
		return super.preHandle(request, response);
	}

	@Override
	protected void postHandle(ServletRequest request, ServletResponse response) throws Exception {
		JWTUtil.localToken.remove();
		super.postHandle(request, response);
	}

	/**
	 * 统一无权限错误处理，不进行basic响应
	 */
	@Override
	protected boolean sendChallenge(ServletRequest request, ServletResponse response) {
		return false;
	}
}
