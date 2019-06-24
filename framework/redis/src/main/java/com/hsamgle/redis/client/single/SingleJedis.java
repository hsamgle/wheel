package com.hsamgle.redis.client.single;

import com.hsamgle.exception.InitException;
import com.hsamgle.redis.client.JedisClient;
import redis.clients.jedis.Jedis;


/**
 *
 *  @feture   :	    TODO		单机版的redis连接模式
 *	@file_name:	    SingleJedis.java
 * 	@packge:	    com.hsamgle.redis.client.single
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:25
 *	@company:		江南皮革厂
 */
public class SingleJedis implements JedisClient {

	/**
	 *
	 * @method:	TODO    获取redis单机版操作对象
	 * @time  :	2018/5/23 17:08
	 * @author:	黄鹤老板
	 * @param
	 * @return:     com.hsamgle.redis.client.JedisClient
	 */
    public static JedisClient getClient(){
    	return new SingleJedis();
    }



    /**
     *
     * @方法功能：	TODO    获取redis连接对象
     * @编写时间：	2018/3/25 15:45
     * @author：	黄先国 | hsamgle@qq.com
     * * @param
     * @return     redis.clients.jedis.Jedis
     */
    @Override
    public  Jedis getJedis() throws InitException {

        Jedis jedis = SingleJedisPool.getJedis();
        if(jedis==null){
            throw new InitException("redis连接异常");
        }
        return jedis;
    }

    /**
     *
     * @方法功能：	TODO    添加缓存
     * @编写时间：	2018/3/25 15:48
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
    * @param value
     * @return     java.lang.String
     */
    @Override
    public  String set ( String key, String value ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.set(key,value);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    添加缓存并设置过期时间
     * @编写时间：	2018/3/25 15:49
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
    * @param value
    * @param expireTime        过期时间，单位秒
     * @return     java.lang.String
     */
    @Override
    public String set ( String key, String value, int expireTime ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            String result  =  jedis.set(key,value);
            jedis.expire(key,expireTime);
            return result;
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    加载缓存
     * @编写时间：	2018/3/25 15:57
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
     * @return     java.lang.String
     */
    @Override
    public String get ( String key ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.get(key);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @method:	TODO    删除指定的key
     * @time  :	2018/6/1 22:14
     * @author:	黄鹤老板
     * @param key
     * @return:     long
     */
    @Override
    public long del(String key){
        Jedis jedis = null;
        try {
             jedis = getJedis();
             return jedis.del(key);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        	SingleJedisPool.returnJedis(jedis);
        }
        return 0;
    }

    /**
     *
     * @方法功能：	TODO    缓存hash对象
     * @编写时间：	2018/3/25 15:58
     * @author：	黄先国 | hsamgle@qq.com
     * * @param hkey
    * @param key
    * @param value
     * @return     long
     */
    @Override
    public long hSet ( String hkey, String key, String value ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return  jedis.hset(hkey,key,value);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    获取hash对象
     * @编写时间：	2018/3/25 15:59
     * @author：	黄先国 | hsamgle@qq.com
     * * @param hkey
    * @param key
     * @return     java.lang.String
     */
    @Override
    public String hGet ( String hkey, String key ){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return  jedis.hget(hkey,key);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    @Override
    public long incr ( String key ){
        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.incr(key);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    设置过期时间
     * @编写时间：	2018/3/25 16:01
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
    * @param second
     * @return     long
     */
    @Override
    public long expire ( String key, int second ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.expire(key,second);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    获取key剩余的生命时间
     * @编写时间：	2018/3/25 16:02
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
     * @return     long
     */
    @Override
    public long ttl ( String key ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.ttl(key);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    判定key是否存在
     * @编写时间：	2018/3/25 16:02
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
     * @return     boolean
     */
    @Override
    public boolean exists ( String key ){

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.exists(key);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

    /**
     *
     * @方法功能：	TODO    删除hash中的值
     * @编写时间：	2018/3/25 16:03
     * @author：	黄先国 | hsamgle@qq.com
     * * @param key
    * @param field
     * @return     long
     */
    @Override
    public long hDel ( String key, String... field ) {

        Jedis jedis = null;
        try {
            jedis = getJedis();
            return jedis.hdel(key,field);
        }finally {
            SingleJedisPool.returnJedis(jedis);
        }
    }

}
