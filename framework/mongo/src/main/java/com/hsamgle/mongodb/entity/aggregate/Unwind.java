package com.hsamgle.mongodb.entity.aggregate;

/**
 *
 *  @feture   :	    TODO
 *	@file_name:	    Unwind.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:38
 *	@company:		江南皮革厂
 */
public final class Unwind extends AggregateOptions {

	private String unwind;

	private Unwind() {}

	public Unwind(String unwind) {
		this.unwind = unwind;
		super.setType(Type.UNWIND);
	}

	public String getUnwind() {
		return unwind;
	}
}
