package com.hsamgle.basic.entity;

import com.hsamgle.basic.utils.JsonUtil;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 *
 *  @feture   :	    TODO    通用实体
 *	@file_name:	    SEntity.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/6 10:57
 *	@company:		江南皮革厂
 */
public class SEntity implements Serializable{

	public String toJson(){
		return JsonUtil.toJson(this);
	}

	public String toPrettyJson(){
		return JsonUtil.toPrettyJson(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}
}
