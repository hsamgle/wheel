package com.hsamgle.mongodb.entity.aggregate;

/**
 *
 *  @feture   :	    TODO		   构建分页查询条件
 *	@file_name:	    Page.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:37
 *	@company:		江南皮革厂
 */
public final class Page extends AggregateOptions {

	private Integer pNow;

	private Integer pSize;

	private int limit;

	private int skip;

	private Page() {}

	public Page(Integer pNow, Integer pSize) {
		this.pNow = pNow;
		this.pSize = pSize;
		super.setType(Type.PAGE);
	}

	public Integer getpNow() {
		return pNow == null ? 1:pNow;
	}

	public Integer getpSize() {
		 return pSize==null?100:pSize;
	}

	public int getLimit() {
		return getpSize();
	}

	public int getSkip() {
		return (getpNow() -1) * getpSize();
	}
}
