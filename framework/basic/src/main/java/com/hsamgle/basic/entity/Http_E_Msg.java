package com.hsamgle.basic.entity;


/**
 *
 *  @feture   :	    TODO		 异常提示信息
 *	@file_name:	    Http_E_Msg.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:12
 *	@company:		江南皮革厂
 */
public final class Http_E_Msg extends DataEntity {

	private static final long serialVersionUID = -644883295173290549L;

	/** 提示信息  */
	private String msg;
	
	private String[] params;

	public Http_E_Msg() {}
	
	public Http_E_Msg(String msg) {this.msg = msg;}
	
	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String... params) {
		this.params = params;
	}

	
	
}
