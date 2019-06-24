package com.hsamgle.mongodb.entity;


/**
 *
 *  @feture   :	    TODO		操作符
 *	@file_name:	    Operators.java
 * 	@packge:	    com.hsamgle.basic.mongodb.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:53
 *	@company:		江南皮革厂
 */
public enum Operators {

	/** 等于 */
	eq, 
	/** 不等于 */
	ne,
	/** 模糊匹配  */
	like,
	/** 左匹配  */
	l_like,
    /** 右匹配  */
    r_like,
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
	/** 指定不加载某字段内容 */
	ingnore,
	/** 排序 */
	sort,
	/** 内嵌对象查询  */
	elem,
	/** 直接加where的查询条件  */
	where,
	/** 直接传入对象作为查询的条件，其中非空字段会生效  */
	obj,
	/** 空间计算  */
	near,
	/////////////////////////////////
	///// 下面是更新操作的操作符//////
	////////////////////////////////
	
	/** 删除指定的一个属性  */
	unset,
	/** 往一个数组添加一个元素 */
	add,
	/** 往一个数组中添加另外一个数组，值的类型为list<T>  */
	addAll,
	/** 删除数组的第一项元素 */
	removeFirst,
	/** 删除数组的最后一个元素 */
	removeLast,
	/** 从数组中删除所有的匹配的内容*/  
    removeAll, 
    /** 数字类型字段递减 */
    dec,
    /** 数字类型字段递增  */
    inc,

    def;


    public static boolean getQuery(Operators operator){
		return operator == ingnore || operator == sort || operator == like || operator == l_like || operator == r_like;
	}

}
