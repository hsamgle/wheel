package com.hsamgle.exception;


/**
 *
 *  @feture   :	    TODO		redis 初始化异常
 *	@file_name:	    InitException.java
 * 	@packge:	    com.hsamgle.exception
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:24
 *	@company:		江南皮革厂
 */
public class InitException extends RuntimeException{

    public InitException ( ) {
    }

    public InitException ( String message ) {
        super(message);
    }
}
