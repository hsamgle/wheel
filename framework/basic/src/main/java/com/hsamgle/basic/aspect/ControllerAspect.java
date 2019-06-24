package com.hsamgle.basic.aspect;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.security.SecurityFilter;
import com.hsamgle.basic.utils.JsonUtil;
import com.hsamgle.basic.utils.LogUtil;
import com.hsamgle.basic.utils.MD5Util;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;


/**
 *
 *  @feture   :	    TODO		控制器AOP切面
 *	@file_name:	    ControllerAspect.java
 * 	@packge:	    com.hsamgle.aspect
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:04
 *	@company:		江南皮革厂
 */
@Aspect
@Component
public class ControllerAspect {

    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void executeService() {}

    private final Set<String> METHODS = new HashSet<>(Arrays.asList("POST", "GET", "DELETE", "PUT"));

    private static Set<String> LastTimeMD5s = new HashSet<>();

    /** 请求原子计算器 */
    private static final AtomicInteger CALCULATOR = new AtomicInteger(1);


    /**
     *
     * @method:	TODO        进行环绕拦截
     * @time  :	2018/3/27 9:05
     * @author:	黄鹤老板
     * @param pjp
     * @return:     ResponseEntity
     */
    @SuppressWarnings("unchecked")
    @Around("executeService()")
    public ResponseEntity doAround( ProceedingJoinPoint pjp) throws Throwable {

	    String currentMD5 = "";
    	try {

	        Object[] args = pjp.getArgs();

	        long start = System.currentTimeMillis();
	        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
	        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
	        HttpServletRequest request = sra.getRequest();

	        String method = request.getMethod();
	        if (!METHODS.contains(method.toUpperCase())) {
	            return ResponseEntity.build(Code.HTTP_405, Code.HTTP_405_MSG);
	        }

	        // 获取当前拦截到的方法
		    Signature signature = pjp.getSignature();
		    MethodSignature methodSignature = (MethodSignature)signature;
		    Method targetMethod = methodSignature.getMethod();


		    // 执行安全校验
	        ResponseEntity securityCheck = SecurityFilter.securityCheck(request,targetMethod);
		    if(securityCheck!=null){
			    return securityCheck;
		    }

	        String uri = request.getRequestURI();
	        String params = JsonUtil.toJson(request.getParameterMap());
	        String token = request.getHeader("token");


	        final int requestNo = CALCULATOR.incrementAndGet();

	        LogUtil.info("\n\n请求令牌: "+token+"\n请求编号: " + requestNo + "  请求方法: " + method + "  请求地址: " + uri + "\n请求参数: " + params);


	        if ( "POST".equalsIgnoreCase(method) ) {
	            String urlParams = uri+params;
	            currentMD5 = MD5Util.getMD5(urlParams);

	            if (LastTimeMD5s.contains(currentMD5)) {
	                LogUtil.error(uri + "  请求重复提交");
	                // 重置摘要值
	                LastTimeMD5s.remove(currentMD5);
	                return ResponseEntity.build(Code.CONTENT_EXISTS, "重复提交请求");
	            }
	            if(!"/files".equals(uri)){
					// 由于前端组件批量提交时有问题，所以这里做了特殊处理
	                LastTimeMD5s.add(currentMD5);
	            }
	        }

	        // result的值就是被拦截方法的返回值
	        Object object = pjp.proceed(args);

	        ResponseEntity result;
	        if ((object instanceof ResponseEntity)) {
	            result = (ResponseEntity) object;
	        } else {
	            return ResponseEntity.build(Code.SERVER_ERR, "请求异常");
	        }

	        long end = System.currentTimeMillis();

	        LogUtil.info("\n请求编号: " + requestNo + "  请求耗时: " + (end - start) + " ms\n请求结果: " + result.toPrettyJson()+"\n");

	        return result;
    	}finally {
		    // 重置摘要值
		    if(!"".equals(currentMD5)){
		        LastTimeMD5s.remove(currentMD5);
		    }
    	}
    }

}
