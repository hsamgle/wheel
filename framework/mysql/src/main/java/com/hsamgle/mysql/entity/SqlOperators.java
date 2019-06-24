package com.hsamgle.mysql.entity;


/**
 * 
 *  @feture   :	    TODO	操作符
 *	@file_name:	    SqlOperators.java
 * 	@packge:	    com.hsamgle.mysql.entity
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/3/27 10:24
 *	@company:		江南皮革厂
 */
public enum SqlOperators {

	/** 等于 */
	eq, 
	/** 不等于 */
	ne,
	/** 模糊匹配  */
	llike,
	rlike,
	like,
	/** 大于 */
	gt, 
	/** 小于 */
	lt,
	/** 大等于 */
	gte,
	/** 小等于 */
	lte, 
	/** 在集合中 */
	in, 
	/** 不在集合中 */
	nin,
	/** 存在某字段 */
	exists,
	/** 不存在某字段  */
	nexists,
	/** 指定加载某字段内容 */
	request,
	/** 排序 */
	sort,
	/** 实体本身就是充当条件  */
	entity
	
}
