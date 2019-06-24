package com.hsamgle.mongodb.exception;

public class MongoException extends RuntimeException{

	private static final long serialVersionUID = 4866647121412049893L;

	
	private String msg;
	
	public MongoException(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	@Override
	public String toString() {
		return "MongoException [msg=" + msg + "]";
	}
}
