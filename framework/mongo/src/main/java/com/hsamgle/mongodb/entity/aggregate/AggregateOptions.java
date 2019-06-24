package com.hsamgle.mongodb.entity.aggregate;

/**
 *
 *  @feture   :	    TODO    数据聚合参数
 *	@file_name:	    AggregateOptions.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:34
 *	@company:		江南皮革厂
 */
public  class AggregateOptions {

	private Type type;

	public enum Type{
		LOOK_UP,
		GROUP,
		MATCH,
		PAGE,
		PROJECT,
		SORT,
		UNWIND,
		GEO
	}

	void setType(Type type){
		this.type = type;
	}

	public Type getType() {
		return type;
	}
}
