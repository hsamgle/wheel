package com.hsamgle.mongodb;

import com.hsamgle.mongodb.exception.MongoException;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ServerAddress;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  @feture   :	    TODO		初始化mongodb连接对象
 *	@file_name:	    MongoDB.java
 * 	@packge:	    com.hsamgle.basic.mongodb
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:19
 *	@company:		江南皮革厂
 */
@Component
public class MongoDB {


	/** 主的默认地址  */
	private static String HOST;
	@Value("${mongodb.host:null}")
	public  void setHOST(String hOST) {
		HOST = hOST;
	}

	/** 数据库名称  */
	private static String DB_NAME;
	@Value("${mongodb.db_name:null}")
	public  void setDB_NAME(String db_name) {
	    DB_NAME = "null".equals(db_name)?null:db_name;
	}

	/** 扫描的包路径 */
    private static String MAP_PACKGE;
    @Value("${mongodb.map_package:null}")
    public  void setMapPackge ( String mapPackge ) {
        MAP_PACKGE = mapPackge;
    }


    /**
     *
     * @method:	TODO    手动初始化Mongodb数据库对象
     * @time  :	2018/3/27 10:19
     * @author:	黄鹤老板
     * @param
     * @return:     boolean
     */
	 public static boolean init(){

		try {
			return initMongo(HOST, DB_NAME);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	private static final Morphia MORPHIA = new Morphia();

	 private static Datastore datastore;

    /**
     *
     * @method:	TODO   初始化mongodb连接
     * @time  :	2018/3/27 10:19
     * @author:	黄鹤老板
     * @param host
    * @param defaultDb
     * @return:     boolean
     */
    private synchronized static boolean initMongo(String host,String defaultDb ) throws Exception{

        if(StringUtils.isEmpty(host) || "null".equals(host)){
            return false;
        }

        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        build.connectionsPerHost(200);
        build.threadsAllowedToBlockForConnectionMultiplier(200);
        build.maxWaitTime(120000);
        build.socketTimeout(0);
        build.cursorFinalizerEnabled(true);
        build.minConnectionsPerHost(10);
        //	线程的闲置时间
        build.maxConnectionIdleTime(30000);
        // 线程的生命周期
        build.maxConnectionLifeTime(180000);
        //与数据库建立连接的timeout设置为1分钟
        build.connectTimeout(30000);
        MongoClientOptions myOptions = build.build();

        //是集群的方式
        List<ServerAddress> addresses = new ArrayList<>();
        String[] hosts = host.split(",");
        for (String h : hosts) {
            String[] hAndp = h.split(":");
            int port = Integer.valueOf(hAndp[1]);
            addresses.add(new ServerAddress(hAndp[0], port));
        }

        MongoClient mongo  = new MongoClient(addresses,myOptions);

        MORPHIA.mapPackage(MAP_PACKGE);
        datastore = MORPHIA.createDatastore(mongo, defaultDb);

        // 初始化失败
        return true;
    }


    static Datastore getDs() throws MongoException {
        if(datastore==null){
            throw new MongoException("mongodb服务尚未初始化");
        }
        return datastore;
    }

}
