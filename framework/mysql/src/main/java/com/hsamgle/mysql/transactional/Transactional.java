package com.hsamgle.mysql.transactional;

import org.springframework.transaction.interceptor.TransactionAspectSupport;


/**
 *
 *  @feture   :	    TODO		设置手动事务回滚
 *	@file_name:	    Transactional.java
 * 	@packge:	    com.hsamgle.mysql.transactional
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:24
 *	@company:		江南皮革厂
 */
public class Transactional {


	public static void rollback(){
		TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	}
	
}
