package com.hsamgle.basic.annotation;

import java.lang.annotation.*;

/**
 *
 *  @feture   :	    TODO	token 信息识别性注解
 *	@file_name:	    Token.java
 * 	@packge:	    com.hsamgle.basic.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/6/7 9:15
 *	@company:		江南皮革厂
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
public @interface Token {

	String tag();

}
