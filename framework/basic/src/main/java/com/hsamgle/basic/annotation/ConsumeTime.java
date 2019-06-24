package com.hsamgle.basic.annotation;

import java.lang.annotation.*;


/**
 *
 *  @feture   :	    TODO	已aop的方式来统计方法执行的耗时
 *	@file_name:	    ConsumeTime.java
 * 	@packge:	    com.hsamgle.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 8:46
 *	@company:		江南皮革厂
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ConsumeTime {

	/**  统计结果打印的标题  */
	String title() default "";
	
}
