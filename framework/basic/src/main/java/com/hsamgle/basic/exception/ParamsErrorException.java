package com.hsamgle.basic.exception;

/**
 * 
 *  @类功能:	TODO	参数错误异常
 *	@文件名:	ParamsMissException.java
 * 	@所在包:	com.com.dplus.project.exception
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年12月2日下午2:08:43
 *	@公_司:		广州讯动网络科技有限公司
 */
public class ParamsErrorException extends RuntimeException{

	private static final long serialVersionUID = -4112393625948442457L;

	/** 错误的提示信息  */
	private String msg;
	
	/** 具体的参数  */
	private String param;
	
	private int code;
	
	public ParamsErrorException(String msg,String param,int code) {
		this.msg = msg;
		this.code  = code;
		this.param = param;
	}

	public String getParam() {
		return param;
	}

	public void setParam(String param) {
		this.param = param;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return "ParamsErrorException [msg=" + msg + ", param=" + param
				+ ", code=" + code + "]";
	}
	
	
}
