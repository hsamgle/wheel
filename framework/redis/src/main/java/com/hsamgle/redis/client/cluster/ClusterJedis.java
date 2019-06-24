package com.hsamgle.redis.client.cluster;

import com.hsamgle.redis.client.JedisClient;
import redis.clients.jedis.Jedis;


/**
 *
 *  @feture   :	    TODO		集群模式，暂时不做实现
 *	@file_name:	    ClusterJedis.java
 * 	@packge:	    com.hsamgle.redis.client.cluster
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:25
 *	@company:		江南皮革厂
 */
public class ClusterJedis implements JedisClient {
    @Override
    public Jedis getJedis() {
        return null;
    }

    @Override
    public String set ( String key, String value ) {
        return null;
    }

    @Override
    public String set ( String key, String value, int expireTime ) {
        return null;
    }

    @Override
    public String get ( String key ) {
        return null;
    }

    @Override
    public long del(String key) {
        return 0;
    }

    @Override
    public long hSet ( String hkey, String key, String value ) {
        return 0;
    }

    @Override
    public String hGet ( String hkey, String key ) {
        return null;
    }

    @Override
    public long incr ( String key ) {
        return 0;
    }

    @Override
    public long ttl ( String key ) {
        return 0;
    }

    @Override
    public boolean exists ( String key ) {
        return false;
    }

    @Override
    public long expire ( String key, int seconds ) {
        return 0;
    }

    @Override
    public long hDel ( String key, String... field ) {
        return 0;
    }
}
