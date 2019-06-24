package com.hsamgle.mongodb.utils;

import org.springframework.util.StringUtils;

/**
 * 
 *  @类功能:	TODO	获取环境变量的工具
 *	@文件名:	EnvUtils.java
 * 	@所在包:	com.com.dplus.accesslayer.utils
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年9月18日上午11:46:10
 *	@公_司:		广州讯动网络科技有限公司
 */
public final class EnvUtils {

	/**
	 * 
	 * @方法功能：	TODO	获取环境变量
	 * @方法名称：	getVal
	 * @编写时间：	上午11:46:50
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param env
	 * @方法参数：	@param def
	 * @方法参数：	@return
	 * @返回值  :	String
	 */
	public static String getVal(String env,String def){
		String target = "";
		try {
			if(!StringUtils.isEmpty(env)){
				if(env.contains("{")){
					String serviceName = env.substring(1, env.lastIndexOf("}"));
					String surfix  = env.substring(env.lastIndexOf("}")+1);
					String envalue = System.getenv(serviceName);
					if(envalue != null){
						String[] strs=envalue.split("/");
						if(strs.length == 2){
							target = System.getenv(strs[1].replace('-', '_').toUpperCase()+surfix);
							target= target!=null?target.replace("tcp://", ""):def;
							System.out.println("\nenv--->"+env+"\ntarget --->"+target);
						}
					}
				}else{
					// 直接获取环境变量
					target = System.getenv(env);
					target = target!=null?target:def;
					System.out.println("\nenv--->"+env+"\ntarget --->"+target);
				}
			}else{
				target = def;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return target.trim();
	}
	
}
