package com.hsamgle.redis.client;


import redis.clients.jedis.Jedis;

/**
 *
 *  @feture   :	    TODO		   redis客户端
 *	@file_name:	    JedisClient.java
 * 	@packge:	    com.hsamgle.redis.client
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/23 17:38
 *	@company:		江南皮革厂
 */
public interface JedisClient {

	Jedis getJedis();

    String set(String key, String value);

    String set(String key, String value,int expireTime);

    String get(String key);

    long del(String key);

    long hSet(String hkey, String key, String value);

    String hGet(String hkey, String key);

    long incr(String key);

    long expire(String key, int second);

    long ttl(String key);

    boolean exists(String key);

    long hDel(String key, String... field);


}