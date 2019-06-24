package com.hsamgle.basic.juhe;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


/**
 *
 *  @feture   :	    TODO		   聚合数据通用配置项
 *	@file_name:	    JuheConfig.java
 * 	@packge:	    com.hsamgle.basic.juhe
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/8 14:35
 *	@company:		江南皮革厂
 */
@Component
public class JuheConfig {

	/** 聚合数据的查询的快递的appkey */
	static String ExpKey;
	@Value("${juhe.key.exp:null}")
	public void setExpKey(String expKey) {
		ExpKey = expKey;
	}

	/** 聚合数据发送短信的key */
	static String SMSKey;
	@Value("${juhe.key.sms:null}")
	public void setSMSKey(String key) {
		SMSKey = key;
	}
}
