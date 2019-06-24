package com.hsamgle.mysql.utils;

/**
 *
 *  @feture   :	    TODO		加载类工具
 *	@file_name:	    ClassUtils.java
 * 	@packge:	    com.hsamgle.mysql.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/10/26 9:26
 *	@company:		江南皮革厂
 */
public final class ClassUtils {

	private static  Class<?> baseEntityClz;

	private static Class<?> mysqlEntityClz;

	public static Class<?> getBaseEntityClass(){
		try {
			if(baseEntityClz==null){
				baseEntityClz =  Class.forName("com.hsamgle.basic.entity.DataEntity");
			}
		}catch (Exception ignore){}
		return baseEntityClz;
	}

	public static Class<?> getMysqlEntityClass(){
		try {
		    if(mysqlEntityClz==null){
			    mysqlEntityClz = Class.forName("com.hsamgle.basic.entity.MysqlEntity");
		    }
		}catch (Exception ignore){}
		return mysqlEntityClz;
	}
}
