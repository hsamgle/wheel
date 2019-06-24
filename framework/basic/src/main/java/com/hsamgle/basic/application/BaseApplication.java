package com.hsamgle.basic.application;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;


/**
 *
 *  @feture   :	    TODO		启动类父类
 *	@file_name:	    BaseApplication.java
 * 	@packge:	    com.hsamgle.basic.application
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:04
 *	@company:		江南皮革厂
 */
@SpringBootApplication(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        DataSourceAutoConfiguration.class
})
@ComponentScan(value = {"${scan.path}","com.hsamgle.*"})
public class BaseApplication {

}
