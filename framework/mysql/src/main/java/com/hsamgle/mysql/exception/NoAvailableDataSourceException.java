package com.hsamgle.mysql.exception;

public class NoAvailableDataSourceException extends BaseMySqlException{

	private static final long serialVersionUID = -784657152997441572L;

	public NoAvailableDataSourceException() {
		super("无可用数据源,请检查配置文件");
	}
	
}
