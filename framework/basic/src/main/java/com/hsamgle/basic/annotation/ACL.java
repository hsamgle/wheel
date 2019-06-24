package com.hsamgle.basic.annotation;

import com.hsamgle.basic.constant.SecurityLevel;

import java.lang.annotation.*;


/**
 *
 *  @feture   :	    TODO		安全级别
 *	@file_name:	    SecurityFilter.java
 * 	@packge:	    com.hsamgle.basic.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/26 12:10
 *	@company:		江南皮革厂
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ACL {

	/** 定义安全级别 */
	SecurityLevel level() default SecurityLevel.NORMAL;

	/**  定义角色  */
	String[] roles() default "";

	/** 定义权限 */
	String[] permission() default "";

}
