package com.hsamgle.basic.entity;

import com.hsamgle.basic.constant.Code;
import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 *  @feture   :	    TODO		返回的请求实体
 *	@file_name:	    ResponseEntity.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/3/27 9:15
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false)
public  class ResponseEntity  extends DataEntity implements Cloneable{

	private static final long serialVersionUID = 3177321630825295950L;


	private static final ResponseEntity RESULT = new ResponseEntity();

    /**
     * 禁止外面进行无参实例化
     */
    private ResponseEntity ( ) {}

    /**
	 * 默认成功的
	 * @param result
	 */
	public static ResponseEntity build(Object result) {
		ResponseEntity entity = getClone();
		entity.code = Code.SUCCESS;
		entity.result = result;
		entity.ok = true;
		return entity;
	}
	
	/**
	 * 非0请求，附带新的结果
	 * @param code
	 */
	public static ResponseEntity build(int code,Object result) {
		ResponseEntity entity = getClone();
		entity.code = code;
		entity.result = result;
		entity.ok = code==0;
		return entity;
	}


	/**
	 * 带参数的异常提示
	 * @param code		异常码
	 * @param msg		提示的消息
	 * @param params	相关的参数
	 */
	public static ResponseEntity build(int code,String msg,String... params) {
		ResponseEntity entity = getClone();
		entity.code = code;
		Http_E_Msg eMsg = new Http_E_Msg();
		eMsg.setMsg(msg);
		eMsg.setParams(params);
		entity.result = eMsg;
		entity.ok = code==0;
		return entity;
	}

	/** 返回的结果码  */
	private int code = 0;

	/** 返回的请求结果数据  */
	private Object result;

	/** 请求是否成功 */
	private boolean ok;

    private static ResponseEntity getClone(){
		try {
			return (ResponseEntity) RESULT.clone();
		}catch (Exception e){
			return new ResponseEntity();
		}
    }


    public boolean ok(){
    	return code==0;
    }

    @SuppressWarnings("unchecked")
	public <T> T getResult(Class<T> t) {
    	if(result==null){
    		return null;
	    }
	    T res = null;
    	try {
    	    res = t.cast(result);
    	}catch (Exception e){
    	    e.printStackTrace();
    	}
		return res;
	}
}
