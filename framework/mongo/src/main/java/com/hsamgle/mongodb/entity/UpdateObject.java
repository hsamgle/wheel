package com.hsamgle.mongodb.entity;

/**
 *
 *  @feture   :	    TODO		封装更新对象
 *	@file_name:	    UpdateObject.java
 * 	@packge:	    com.hsamgle.basic.mongodb.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:54
 *	@company:		江南皮革厂
 */
public class UpdateObject {

	private String field;
	
	private Operators operators;
	
	private Object value;
	
	public UpdateObject() {
	}
	
	
	public UpdateObject(String field,Object value) {
		this.field = field;
		this.operators = Operators.eq;
		this.value = handleNull(value);
	}
	public UpdateObject(String field,Operators operators) {
		this.field = field;
		this.operators = operators;
	}
	
	public UpdateObject(String field,Operators operators,Object value) {
		this.field = field;
		this.operators = operators;
		this.value = handleNull(value);
	}


	public String getField() {
		return field;
	}


	public void setField(String field) {
		this.field = field;
	}


	public Operators getOperators() {
		return operators;
	}


	public void setOperators(Operators operators) {
		this.operators = operators;
	}


	public Object getValue() {
		return value;
	}


	public void setValue(Object value) {
		this.value = handleNull(value);
	}


	@Override
	public String toString() {
		return "UpdateObject [field=" + field + ", operators=" + operators
				+ ", value=" + value + "]";
	}

	private Object handleNull(Object value){
		if("NULL".equals(value) && value.getClass().getName().contains("String")){
			return "";
		}
		return value;
	}

}
