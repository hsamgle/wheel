package com.hsamgle.redis.client.single;

import com.hsamgle.redis.client.RedClient;
import org.redisson.RedissonWriteLock;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

/**
 * 
 *  @feture   :	    TODO		
 *	@file_name:	    SingleRedission.java
 * 	@packge:	    com.hsamgle.redis.client.single
 *	@author:	    黄鹤老板 
 *  @create_time:	2019/1/26 15:44
 *	@company:		江南皮革厂
 */
public class SingleRedission implements RedClient{


	public static RedClient getRedClient() {
		return new SingleRedission();
	}

	@Override
	public RedissonClient getRedissonClient() {
		return SingleJedisPool.getRedissonClient();
	}

	@Override
	public RReadWriteLock getReadWriteLock(String key) {
		return getRedissonClient().getReadWriteLock(key);
	}

	@Override
	public RLock getReadLock(String key) {
		return getReadWriteLock(key).readLock();
	}

	@Override
	public RLock getWriteLock(String key) {
		return getReadWriteLock(key).writeLock();
	}
}
