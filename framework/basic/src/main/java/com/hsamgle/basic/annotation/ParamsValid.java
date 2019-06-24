package com.hsamgle.basic.annotation;

import java.lang.annotation.*;


/**
 *
 *  @feture   :	    TODO		controller 参数的校验
 *	@file_name:	    ParamsValid.java
 * 	@packge:	    com.hsamgle.basic.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:42
 *	@company:		江南皮革厂
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER,ElementType.FIELD})
@Documented
public @interface ParamsValid {

   /**
     *
     * @method:	TODO    参数的正则表达式
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	String reg() default "";
	
	
    /**
     *
     * @method:	TODO    空异常时附带提示信息
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	String nullMsg() default "";
	

    /**
     *
     * @method:	TODO    判断参数是否为空，默认加这注解  false  是不能为空的  false 是允许为空
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	boolean notNull() default false;


    /**
     *
     * @method:	TODO    当前的参数类型是否为整型
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	boolean isInt() default false;


    /**
     *
     * @method:	TODO    当期的参数类型是否为浮点型
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	boolean isFloat() default false;
	
	
	

    /**
     *
     * @method:	TODO    当前的参数的最小值是否小于某值
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	int min() default -1;


    /**
     *
     * @method:	TODO    当前的参数的最大值是否大于某值
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	int max() default -1;
	
	


    /**
     *
     * @method:	TODO    验证当前参数的最小长度
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	int minLen() default -1;
	
	


    /**
     *
     * @method:	TODO    验证当前参数的最大长度
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	int maxLen() default -1;
	

    /**
     *
     * @method:	TODO    是否为BaseEntity实例参数
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	boolean isEntity() default false;
	

    /**
     *
     * @method:	TODO    这个标签结合  isEntity()  使用，传入需要校验的参数名称集合
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	String[] needArgs() default {};
	

    /**
     *
     * @method:	TODO    排除某些参数
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	String[] exclude() default {};
	

	/** 过滤字段   一般用在实体里面的*/
	boolean filters() default  false;

    /**
     *
     * @method:	TODO    判断是不是时间通用的时间格式
     * @time  :	2018/3/27 8:49
     * @author:	黄鹤老板
     * @params:
     * @return:
     */
	boolean isTime() default false;


	/**
	 * 对参数设置别名
	 * @return
	 */
	String alias() default "";

	/**
	 * 参数内容分隔，需要接收的参数类型是数组或者集合类型
	 * @return
	 */
	String split() default "";

	/** 默认值  */
	String def() default "";

}