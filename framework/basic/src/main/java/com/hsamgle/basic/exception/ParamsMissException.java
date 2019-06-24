package com.hsamgle.basic.exception;

/**
 * 
 *  @类功能:	TODO	参数缺失异常
 *	@文件名:	ParamsMissException.java
 * 	@所在包:	com.com.dplus.project.exception
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年12月2日下午2:08:43
 *	@公_司:		广州讯动网络科技有限公司
 */
public class ParamsMissException extends RuntimeException{

	private static final long serialVersionUID = -4112393625948442457L;

	private String msg = "必要参数丢失";
	
	private String param;
	
	public ParamsMissException(String param,String msg) {
		this.param = param;
		this.msg = msg;
	}
	
	public ParamsMissException(String param) {
		this.param = param;
	}

	public String  getParam() {
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

	@Override
	public String toString() {
		return "ParamsMissException [msg=" + msg + ", param=" + param + "]";
	}
	
	
}
