package com.water.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/***
 * 
 * @ClassName:  SystemFilter   
 * @Description:系统过滤器   
 * @author: 张凯强
 * @date:   Mar 2, 2020 6:05:06 PM
 */
public class SystemFilter implements Filter {

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException,
            ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
//        System.out.println(request.getRequestURL());
        String basePath = request.getContextPath();
        request.setAttribute("basePath", basePath);
        
		//Servlet 2.5不支持在Cookie上直接设置HttpOnly属性
		//为SetCookie配置secure属性 add date :20190530
     	StringBuilder builder = new StringBuilder();
     	builder.append("Secure;HttpOnly; ");
     	response.addHeader("Set-Cookie", builder.toString());
     	//设置X-Frame-Options头。
     	response.addHeader("Set-Cookie", "cookiename=value;Path=/;Domain=domainvalue;Max-Age=seconds;Secure;HTTPOnly");
     	
     	response.setHeader("Set-Cookie", "name=value; secure; HttpOnly");//设置HttpOnly属性,防止Xss攻击  
     	response.setHeader("X-Frame-Options", "SAMEORIGIN");//设置x-frame-options
//     	response.addHeader("X-Frame-Options","DENY");
//        resp.addHeader("X-XSS-Protection", "X-XSS-Protection: 1; mode=block");
     	response.addHeader("X-XSS-Protection", "1; mode=block");
     	response.addHeader("X-Content-Type-Options", "nosniff");
        if (!SecurityUtils.getSubject().isAuthenticated()) {
            //判断session里是否有用户信息
            if (request.getHeader("x-requested-with") != null
                    && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")) {
                //如果是ajax请求响应头会有，x-requested-with
                response.setHeader("session-status", "timeout");//在响应头设置session状态
                return;
            }else {
            	String msg = "请登录";
            	response.getOutputStream().write(msg.getBytes());
            	//WebUtils.issueRedirect(request, response, "/reLogin");
            	return;
            }
        }
        filterChain.doFilter(request, servletResponse);

    }

    @Override
    public void destroy() {

        // TODO Auto-generated method stub

    }

    @Override
    public void init(FilterConfig arg0) throws ServletException {

        // TODO Auto-generated method stub

    }

}