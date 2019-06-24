package com.hsamgle.mysql.exception;

public class BaseMySqlException extends Throwable{

	private static final long serialVersionUID = -8429174183306661417L;

	public BaseMySqlException() {}
	
	private String msg;
	
	public BaseMySqlException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	
}
