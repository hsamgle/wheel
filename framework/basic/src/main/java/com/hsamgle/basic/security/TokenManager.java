package com.hsamgle.basic.security;

import com.hsamgle.basic.constant.Const;
import com.hsamgle.basic.entity.TokenInfo;
import com.hsamgle.basic.utils.DateTimeUtils;
import com.hsamgle.basic.utils.JsonUtil;
import com.hsamgle.basic.utils.MD5Util;
import com.hsamgle.basic.utils.RandomUtil;
import com.hsamgle.redis.client.JedisClient;
import com.hsamgle.redis.client.single.SingleJedis;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 *
 *  @feture   :	    TODO		令牌管理工具
 *	@file_name:	    TokenManager.java
 * 	@packge:	    com.hsamgle.basic.security
 *	@author:	    黄鹤老板
 *  @create_time:	2018/6/1 22:10
 *	@company:		江南皮革厂
 */
public final class TokenManager {


	private static final Map<String,Class> TOKEN_ADAPTER = new HashMap<>();

	/**
	 *
	 * @method:	TODO        注册令牌
	 * @time  :	2018/6/7 9:32
	 * @author:	黄鹤老板
	 * @param tag
	* @param t
	 * @return:     void
	 */
	public static <T extends TokenInfo> void register(String tag,Class<T> t){
		if(!TOKEN_ADAPTER.containsKey(tag)){
			TOKEN_ADAPTER.put(tag,t);
		}
	}



	/**
	 *
	 * @method:	TODO    校验token令牌合法性
	 * @time  :	2018/3/27 9:33
	 * @author:	黄鹤老板
	 * @param
	 * @return:     boolean
	 */
	static boolean checkToken(String token){

		try {
			JedisClient client =  SingleJedis.getClient();
			if(!StringUtils.isEmpty(token)){

				// 获取redis中缓存的内容
				String data = client.get(token);
				TokenInfo tokenInfo = JsonUtil.toObject(data,TokenInfo.class);

				if(tokenInfo!=null){
					// 每次检验通过之后会进行需要续活
					if(token.endsWith("_r")){
						// 续活两个星期
						client.set(token,data,Const.T2WEEKS);
					}else{
						// 续活30分钟
						client.set(token,data,Const.T30M);
					}

					return true;
				}
			}
		}catch (Exception e){
			e.printStackTrace();
		}
		return false;
	}


	/**
	 *
	 * @method:	TODO    用户登录之后根据用户信息生成token
	 * @time  :	2018/5/23 17:20
	 * @author:	黄鹤老板
	 * @param
	 * @return:     com.hsamgle.basic.security.Token
	 */
	public static <T extends TokenInfo> T generateToken(T info, boolean remember) throws Exception {
		Object userId = info.getUserId();
		String token = "TK_"+ MD5Util.GetMD5Code(userId + RandomUtil.getNumAndAZ(5)+System.nanoTime())+"_"+RandomUtil.getNumAndAZ(5)+(remember?"_r":"");
		JedisClient client =  SingleJedis.getClient();
		info.setUserId(userId);
		info.setCreateTime(DateTimeUtils.getNowInMillis());
		info.setToken(token);
		client.set(token,info.toJson());
		return info;
	}


	/**
	 *
	 * @method:	TODO        用户的登出时进行清理token
	 * @time  :	2018/6/1 22:12
	 * @author:	黄鹤老板
	 * @param token
	 * @return:     void
	 */
	public static void deleteToken(String token) throws Exception{
		JedisClient client = SingleJedis.getClient();
		client.del(token);
	}


	/**
	 *
	 * @method:	TODO    根据token来查询token缓存的信息
	 * @time  :	2018/6/7 9:09
	 * @author:	黄鹤老板
	 * @param token
	 * @return:     java.lang.String
	 */
	@SuppressWarnings({"unchecked"})
	public static <T extends TokenInfo> T  getTokenInfo(String token) throws Exception {
		JedisClient client = SingleJedis.getClient();
		String data = client.get(token);
		if(!StringUtils.isEmpty(data)){
			TokenInfo tokenInfo = JsonUtil.toObject(data, TokenInfo.class);
			if(tokenInfo!=null){
				Class<T> t = TOKEN_ADAPTER.get(tokenInfo.getTag());
				return JsonUtil.toObject(data, t);
			}
		}
		return null;
	}

}
