package com.hsamgle.redis.client.single;

import com.hsamgle.redis.client.JedisClient;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;


/**
 *
 *  @feture   :	    TODO		单机版的redis线程池
 *	@file_name:	    SingleJedisPool.java
 * 	@packge:	    com.hsamgle.redis.client.single
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:25
 *	@company:		江南皮革厂
 */
@Component
public class SingleJedisPool {


	/** redis的host域名*/
	private static String HOST;
	@Value("${redis.host:null}")
	public  void setHOST(String hOST) {
		HOST = hOST;
	}

	/** redis的端口  */
	private static int PORT;
	@Value("${redis.port:6379}")
	public  void setPORT ( String port ) {
		PORT = Integer.valueOf(port);
	}

	/** 连接的密码  */
	private static String AUTH;
	@Value("${redis.auth:null}")
	public void setAUTH(String aUTH) {
		AUTH = aUTH;
	}

	private static JedisPool jedisPool = null;

	private static RedissonClient  redissonClient = null;

	//可用连接实例的最大数目，默认值为8；
	//如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
	private static final int MAX_ACTIVE = 1024;

	//控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
	private static final int MAX_IDLE = 100;

	//等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException；
	private static final int MAX_WAIT = 10000;

	private static final int TIMEOUT = 10000;

	//在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
	private static final boolean TEST_ON_BORROW = true;

    /**
     *
     * @method:	TODO    执行初始化工作
     * @time  :	2018/3/27 10:25
     * @author:	黄鹤老板
     * @param
     * @return:     boolean
     */
	public static synchronized boolean init(){

		try {
			JedisPoolConfig config = new JedisPoolConfig();
			config.setMaxTotal(MAX_ACTIVE);
			config.setMaxIdle(MAX_IDLE);
			config.setMaxWaitMillis(MAX_WAIT);
			config.setTestOnBorrow(TEST_ON_BORROW);
			if(!StringUtils.isEmpty(AUTH)){
				jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT, AUTH);
			}else{
				jedisPool = new JedisPool(config, HOST, PORT, TIMEOUT);
			}

			// 初始化 Redission 这个是用来实现基于redis的读写锁
			Config redConfig = new Config();
			redConfig.useSingleServer().setAddress("redis://"+HOST+":"+PORT);
			redissonClient =  Redisson.create(redConfig);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}


    /**
     *
     * @method:	TODO    获取redis的操作对象
     * @time  :	2018/3/27 10:25
     * @author:	黄鹤老板
     * @param
     * @return:     redis.clients.jedis.Jedis
     */
	 static synchronized Jedis getJedis(){

	    if(jedisPool!=null){
            return jedisPool.getResource();
        }
        return null;
    }

    static RedissonClient getRedissonClient(){
	 	return redissonClient;
    }

    /**
     *
     * @method:	TODO     归还redis连接
     * @time  :	2018/3/27 10:26
     * @author:	黄鹤老板
     * @param jedis
     * @return:     void
     */
    static void returnJedis(Jedis jedis) {
        if ( jedis != null ) {
            jedis.close();
        }
    }
}
