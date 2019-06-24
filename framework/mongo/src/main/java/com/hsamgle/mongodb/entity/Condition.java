package com.hsamgle.mongodb.entity;


public final class Condition {

	/** 操作的列对象  */
	private String key;
	
	/** 操作符  */
	private Operators operator;
	
	/** 条件的值  */
	private Object value;

	private boolean or;

	public Condition(Object value) {
		this.operator = Operators.obj;
		this.value = value;
	}
	public Condition(String key, Operators operator) {
		this.key = key;
		this.operator = operator; 
	}
	
	public Condition(String key, Object value) {
		this.key = key;
		//默认是相等
		this.operator = Operators.eq;
		this.value = value;
	}
	public Condition(Operators operator, Object value ) {
		this.operator = operator;
		this.value = value;
	}

	public Condition(String key, Operators operator, Object value) {
		this.key = key;
		this.operator = operator;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public Operators getOperator() {
		return operator;
	}

	public Object getValue() {
		return value;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public boolean isOr() {
		return or;
	}

	public Condition or(){
		this.or = true;
		return this;
	}

    @Override
    public String toString ( ) {
        return "Condition{" +
                "key='" + key + '\'' +
                ", operator=" + operator +
                ", value=" + value +
                '}';
    }

}
