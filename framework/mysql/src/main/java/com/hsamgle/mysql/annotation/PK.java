package com.hsamgle.mysql.annotation;

import com.hsamgle.mysql.constant.PKType;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;


/**
 *
 *  @feture   :	    TODO		    主键的类型
 *	@file_name:	    PK.java
 * 	@packge:	    com.hsamgle.mysql.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/9/10 17:18
 *	@company:		江南皮革厂
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Component
public @interface PK {

	/** 主键类型  */
	PKType generator() default PKType.ORDER;

}
