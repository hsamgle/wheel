package com.hsamgle.redis.client;

import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;

/**
 * 
 *  @feture   :	    TODO		
 *	@file_name:	    RedClient.java
 * 	@packge:	    com.hsamgle.redis.client
 *	@author:	    黄鹤老板 
 *  @create_time:	2019/1/26 15:43
 *	@company:		江南皮革厂
 */
public interface RedClient {

	RedissonClient getRedissonClient();

	RReadWriteLock getReadWriteLock(String key);

	RLock getReadLock(String key);

	RLock getWriteLock(String key);

}
