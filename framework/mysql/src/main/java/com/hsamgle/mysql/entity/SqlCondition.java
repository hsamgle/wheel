package com.hsamgle.mysql.entity;
/**
 *
 *  @feture   :	    TODO		封装SQL条件
 *	@file_name:	    SqlCondition.java
 * 	@packge:	    com.hsamgle.mysql.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:23
 *	@company:		江南皮革厂
 */
public class SqlCondition {

	/** 操作的列对象  */
	private String Key;
	
	/** 操作符  */
	private SqlOperators operator;
	
	/** 条件的值  */
	private Object value;
	
	public SqlCondition(String Key,SqlOperators operator) {
		this.Key = Key;
		this.operator = operator; 
	}
	public SqlCondition(Object value) {
		this.operator = SqlOperators.entity;
		this.value = value;
	}
	
	public SqlCondition(String Key,Object value) {
		this.Key = Key;
		this.operator = SqlOperators.eq;  //默认是相等
		this.value = value;
	}
	
	public SqlCondition(String Key,SqlOperators operator,Object value) {
		this.Key = Key;
		this.operator = operator;
		this.value = value;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public SqlOperators getOperator() {
		return operator;
	}

	public void setOperator(SqlOperators operator) {
		this.operator = operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "Condition [Key=" + Key + ", operator=" + operator + ", value="
				+ value + "]";
	}
	
}
