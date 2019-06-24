package com.hsamgle.mongodb.entity.aggregate;


import com.hsamgle.mongodb.entity.Condition;

/**
 *
 *  @feture   :	    TODO		封装数据聚合统计时的查询条件
 *	@file_name:	    Match.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:37
 *	@company:		江南皮革厂
 */
public final class Match extends AggregateOptions {

	private Condition[] conditions;

	private Match() {}

	public Match(Condition[] conditions) {
		this.conditions = conditions;
		super.setType(Type.MATCH);
	}

	public Condition[] getConditions() {
		return conditions;
	}
}
