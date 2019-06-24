package com.hsamgle.basic.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName cn.com.dplus.project.entity
 * @ClassName
 * @Description
 * @date 2018/9/26 11:32
 * @company 广州讯动网络科技有限公司
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Setter(AccessLevel.NONE)
public  class RequestEntity extends SEntity{

	private RequestMethod method;

	private String url;

	private Map<String,Object> headers;

	private Map<String,Object> path;

	private Map<String,Object> query;

	private Map<String,Object> formData;

	private RequestEntity() {}

	public RequestEntity(String url) {
		this.method = RequestMethod.GET;
		this.url = url;
	}

	public RequestEntity(RequestMethod method, String url) {
		this.method = method;
		this.url = url;
	}

	public RequestEntity addPath(String key,Object value){
		if(this.path==null){
			this.path = new HashMap<>();
		}
		this.path.put(key,value);
		return this;
	}


	public RequestEntity addHeader(String key,Object value){
		if(this.headers==null){
			this.headers = new HashMap<>();
		}
		this.headers.put(key,value);
		return this;
	}

	public RequestEntity addQuery(String key,Object value){
		if(this.query == null){
			this.query = new HashMap<>();
		}
		this.query.put(key,value);
		return this;
	}

	public RequestEntity addFormData(String key,Object value){
		if(this.formData==null){
			this.formData = new HashMap<>();
		}
		this.formData.put(key,value);
		return this;
	}


}
