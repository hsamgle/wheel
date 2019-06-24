package com.hsamgle.basic.entity;

import com.hsamgle.basic.utils.MD5Util;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 *
 *  @feture   :	    TODO		系统权限许可
 *	@file_name:	    License.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/16 10:50
 *	@company:		江南皮革厂
 */
@Data
public class License extends SEntity{

	/** 许可路由 */
	private String route;

	/** 提交方法 */
	private Integer method;


	private License() {}

	public License(String route, Integer method) {
		this.route = route;
		this.method = method;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) {return true;}
		if (o == null || getClass() != o.getClass()) {return false;}
		if (!super.equals(o)) {return false;}

		License license = (License) o;

		return route.equals(license.route) && method.equals(license.method);
	}

	@Override
	public int hashCode() {
		return MD5Util.getMD5(route + method).hashCode();
	}
}
