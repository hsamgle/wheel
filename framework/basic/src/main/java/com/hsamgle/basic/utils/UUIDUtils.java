package com.hsamgle.basic.utils;

import java.util.UUID;


/**
 *
 *  @feture   :	    TODO		产生UUID随机数的工具
 *	@file_name:	    UUIDUtils.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:51
 *	@company:		江南皮革厂
 */
public final class UUIDUtils {


	/**
	 *
	 * @method:	TODO    产生指定长度的UUID字符串
	 * @time  :	2018/3/27 9:51
	 * @author:	黄鹤老板
	 * @param size
	 * @return:     java.lang.String
	 */
	public static String getUUID(int size){

		return UUID.randomUUID().toString().replace("-", "").substring(0, size);
	}

	/**
	 *
	 * @method:	TODO    产生UUID字符串
	 * @time  :	2018/3/27 9:51
	 * @author:	黄鹤老板
	 * @param
	 * @return:     java.lang.String
	 */
	public static String getUUID(){
		return UUID.randomUUID().toString().replace("-", "");
	}
	
}
