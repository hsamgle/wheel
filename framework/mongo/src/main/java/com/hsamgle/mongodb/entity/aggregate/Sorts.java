package com.hsamgle.mongodb.entity.aggregate;

import org.mongodb.morphia.query.Sort;

/**
 *
 *  @feture   :	    TODO		   构建聚合统计时的排序规则
 *	@file_name:	    Sorts.java
 * 	@packge:	    com.hsamgle.mongodb.entity.aggregate
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:38
 *	@company:		江南皮革厂
 */
public final class Sorts extends  AggregateOptions{

	private Sort[] sorts;

	private Sorts() {}

	public Sorts(Sort[] sorts) {
		this.sorts = sorts;
		super.setType(Type.SORT);
	}

	public Sort[] getSorts() {
		return sorts;
	}
}
