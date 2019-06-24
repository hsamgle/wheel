package com.hsamgle.basic.controller;


import com.hsamgle.basic.entity.TokenInfo;
import com.hsamgle.basic.security.TokenManager;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 *
 *  @feture   :	    TODO		 控制器父类
 *	@file_name:	    BaseController.java
 * 	@packge:	    com.hsamgle.basic.controller
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:08
 *	@company:		江南皮革厂
 */
@RestController
public class BaseController {


	@Autowired
	private HttpServletRequest request;

	/**
	 *
	 * @method:	TODO    获取当前操作的用户token信息
	 * @time  :	2018/6/7 9:40
	 * @author:	黄鹤老板
	 * @return:     T
	 */
	public   <T extends TokenInfo> T getTokenInfo(){
		try {

			String token = request.getHeader("token");
			if(token!=null){
				return TokenManager.getTokenInfo(token);
			}
		}catch (Exception e){
		    e.printStackTrace();
		}
		return null;
	}

	/**
	 * 获取调用者的IP
	 * @return
	 */
	protected String getIpAddr() {
		String ip = request.getHeader("X-Forwarded-For");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(StringUtils.isNotEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		return request.getRemoteAddr();
	}


}
