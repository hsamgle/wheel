package com.hsamgle.basic.fs;

import java.io.InputStream;

/**
 *
 *  @feture   :	    TODO	第三方文件存储服务
 *	@file_name:	    BaseFileHelper.java
 * 	@packge:	    com.hsamgle.basic.fs
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/26 12:57
 *	@company:		江南皮革厂
 */
interface  BaseFileHelper {

	static final String DOT = ".";

	/**
	 * 执行文件存储
	 */
	String upload(String fileName,InputStream stream) throws Exception;


	/** 执行文件下载 */
	 InputStream download(String fileName) throws Throwable;

	/**
	 * 删除文件
	 */
	boolean delete(String fileName) throws Throwable;




}
