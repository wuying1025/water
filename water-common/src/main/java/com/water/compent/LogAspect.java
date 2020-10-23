package com.water.compent;

import java.time.LocalDateTime;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.water.module.log.entity.TOperationlog;
import com.water.module.log.service.impl.TOperationlogService;
import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.google.gson.Gson;

/***
 * 
 * @ClassName:  LogAspect   
 * @Description:日志切面类   
 * @author: 张凯强
 * @date:   Mar 27, 2020 10:59:49 PM
 */
@Aspect
@Component
public class LogAspect {

	@Autowired
	private TOperationlogService logService;
	
	
	private Logger logger = LoggerFactory.getLogger(LogAspect.class);

	// 切点表达式
	@Pointcut("execution(public * com.inspur.module.**.controller..*(..))")
	public void log() {
	}

	@AfterReturning(value = "log()", returning = "result")
	public void logReturn(JoinPoint joinPoint, Object result) {
		MethodInvocationProceedingJoinPoint  point = (MethodInvocationProceedingJoinPoint)joinPoint;
		Object[] args = joinPoint.getArgs();
		TOperationlog log = new TOperationlog();
		log.setFunctionname(point.getSignature().getDeclaringTypeName());
		log.setOperationtype(point.getSignature().getName());
		if(ArrayUtils.isNotEmpty(args)){
			String param = new Gson().toJson(args);
			if(param.length() < 100) {
				log.setOperationcontent("请求参数:" + new Gson().toJson(args) );
			}
				
		}
		log.setCreateddate(LocalDateTime.now());
		logService.save(log);
	}

	@AfterThrowing(value = "log()", throwing = "exception")
	public void logException(JoinPoint joinPoint, Exception exception) {
		//OperationLog log = createLog(joinPoint);

		Object[] args = joinPoint.getArgs();
		
		Object param = null;
		
		if(ArrayUtils.isNotEmpty(args)) {
			param = args[0];
		}
		TOperationlog log = new TOperationlog();
		log.setFunctionname(joinPoint.getSignature().getDeclaringTypeName()+"."+joinPoint.getSignature().getName());
		log.setOperationcontent("请求参数:" + param + ",异常:" + exception.getMessage());
		log.setCreateddate(LocalDateTime.now());
		logService.save(log);
	}
		

}
