package com.hsamgle.mysql.config;

import ex.com.hsamgle.mysql.base.BaseMapper;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tk.mybatis.spring.mapper.MapperScannerConfigurer;

import java.util.Properties;


/**
 *
 *  @feture   :	    TODO		mapper 初始化配置
 *	@file_name:	    MapperScanner.java
 * 	@packge:	    com.hsamgle.mysql.config
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:21
 *	@company:		江南皮革厂
 */
@Configuration
@AutoConfigureAfter(MybatisAutoConfiguration.class)
public class MapperScanner {

	@Bean
	public MapperScannerConfigurer mapperScannerConfigurer() {
		MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
		mapperScannerConfigurer.setSqlSessionFactoryBeanName("sqlSessionFactory");
		mapperScannerConfigurer.setBasePackage("**.dao");
		Properties properties = new Properties();
		// 这里要特别注意，不要把MyMapper放到 basePackage 中，也就是不能同其他Mapper一样被扫描到。
		properties.setProperty("mappers", BaseMapper.class.getName());
		properties.setProperty("notEmpty", "false");
		properties.setProperty("IDENTITY", "MYSQL");
		mapperScannerConfigurer.setProperties(properties);
		return mapperScannerConfigurer;
	}
	
}
