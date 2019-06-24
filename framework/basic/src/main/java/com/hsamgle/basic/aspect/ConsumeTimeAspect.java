package com.hsamgle.basic.aspect;

import com.hsamgle.basic.annotation.ConsumeTime;
import com.hsamgle.basic.utils.LogUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;


/**
 *
 *  @feture   :	    TODO		统计方法消耗时间的切面
 *	@file_name:	    ConsumeTimeAspect.java
 * 	@packge:	    com.hsamgle.aspect
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:04
 *	@company:		江南皮革厂
 */
@Aspect
@Component
public class ConsumeTimeAspect {


		@Around("@annotation(com.hsamgle.basic.annotation.ConsumeTime)")
	    public Object doAround(ProceedingJoinPoint pjp) throws Throwable{
			long start = System.currentTimeMillis();
			try {
				return pjp.proceed();
			}finally{
				long end = System.currentTimeMillis();
		        String methodName=pjp.getSignature().getName();  
		        Class<?> classTarget=pjp.getTarget().getClass();  
		        Class<?>[] par=((MethodSignature) pjp.getSignature()).getParameterTypes();  
		        Method objMethod=classTarget.getMethod(methodName, par);  
		        ConsumeTime time=objMethod.getAnnotation(ConsumeTime.class);
		        String title = time.title();
		        LogUtil.info("执行方法      "+((StringUtils.isEmpty(title)?methodName:title)+"     耗时---->   "+(end - start)+"  ms"));
			}
		}
}
