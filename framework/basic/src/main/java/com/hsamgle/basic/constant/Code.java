package com.hsamgle.basic.constant;


/**
 *
 *  @feture   :	    TODO		这里封装了HTTP返回的结果码
 *	@file_name:	    Code.java
 * 	@packge:	    com.hsamgle.basic.constant
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:07
 *	@company:		江南皮革厂
 */
public  class Code {

	/** 标准成功返回  */
	public final static int SUCCESS = 0;


    /*******************************************************************/
    /**                      1 - 10 是权限错误                          */
    /*******************************************************************/

    /** token 无效或不存在  */
    public static final int TOKEN_INVALID = 1;
    public static final String TOKEN_INVALID_MSG = "token 无效或不存在";

    /** 请求校验失败  */
    public static final int SIGN_ERR = 2;
    public static final String SIGN_ERR_MSG = "请求校验失败";

    /** 无权限操作  */
    public static final int PROHIBITED = 3;
    public static final String PROHIBITED_MSG = "无权限操作";

    /** 违规操作  */
    public static final int ILLEGAL = 4;
    public static final String ILLEGAL_MSG = "当前操作有违规嫌疑";


    /*******************************************************************/
    /**                      50 - 59 是参数错误                         */
    /*******************************************************************/

    /** 参数错误  */
    public final static int PARAMS_ERR = 50;

    /** 参数缺失  */
    public final static int PARAMS_MISS = 51;

    /** 参数类型错误  */
    public final static int PARAM_TYPE_ERR = 52;

    /** 参数格式错误  */
    public final static int PARAM_FORMAT_ERR = 53;

    /** 字符串参数长度错误  */
    public final static int PARAM_LEN_ERR = 54;

    /** 数字参数大小值错误  */
    public final static int PARAM_VAL_ERR = 55;

    /** 参数范围错误  */
    public final static int PARAM_RANGE_ERR = 56;



    /*******************************************************************/
    /**                      60 - 69 是内容错误                         */
    /*******************************************************************/

    /** 内容不存在  */
    public final static int NO_RESULT = 60;
    public final static String NO_RESULT_MSG = "无相关数据";

    /** 内容已存在  */
    public final static int CONTENT_EXISTS = 61;
    public final static String CONTENT_EXISTS_MSG = "内容已存在";

    public final static int CONTENT_USED = 62;

    public final static int CONTENT_NOT_USED = 63;

	/**  查询繁忙  */
	public final static int PLZ_TRYAGAIN_LATER = 64;
	public final static String PLZ_TRYAGAIN_LATER_MSG = "服务繁忙，请稍后重试";


    /****************************************************************************************************/
    /****************************************************************************************************/
    /*************************     错误码100以内的为保留值， 自定义必须大于100       ***********************/
    /****************************************************************************************************/
    /****************************************************************************************************/




    /*******************************************************************/
    /**                      400~  是系统错误                           */
    /*******************************************************************/

    /** 第三方api调用异常 */
    public static final int TP_API_INVOKE_ERR = 401;
    public static final String TP_API_INVOKE_ERR_MSG = "第三方请求失败,因为:  ";


	/** 服务器资源尚未初始化 */
	public static final int SERVER_403 = 403;
	public static final String SERVER_403_MSG = "服务器资源尚未初始化";


	/** 无效的请求 */
	public static final int HTTP_404 = 404;
	public static final String HTTP_404_MSG = "请求不存在";

    /** 不被允许的提交方法  */
    public final static int HTTP_405 = 405;
    public final static String HTTP_405_MSG = "不被允许的提交方法";

	/** 服务器异常 */
	public final static int SERVER_ERR = 500;
	public final static String SERVER_ERR_MSG = "系统繁忙或异常";

}
