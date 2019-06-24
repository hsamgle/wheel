package com.hsamgle.mysql.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 *  @feture   :	    TODO		重写 SqlSessionFactoryBean  目的是解决typeAliasesPackage 通配符的问题
 *	@file_name:	    PackagesSqlSessionFactoryBean.java
 * 	@packge:	    com.hsamgle.mysql.config
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/27 17:16
 *	@company:		江南皮革厂
 */
public class PackagesSqlSessionFactoryBean extends SqlSessionFactoryBean {

	private static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

	@Override
	public void setTypeAliasesPackage(String typeAliasesPackage) {
		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
		MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);
		String[] packges = typeAliasesPackage.split(",");
		//将加载多个绝对匹配的所有Resource
		//将首先通过ClassLoader.getResource("META-INF")加载非模式路径部分
		//然后进行遍历模式匹配
		try {
			List<String> result = new ArrayList<>();
			for (String typeAlias : packges) {
				typeAlias = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
						ClassUtils.convertClassNameToResourcePath(typeAlias) + "/" + DEFAULT_RESOURCE_PATTERN;

				Resource[] resources =  resolver.getResources(typeAlias);
				if(resources.length > 0){
					MetadataReader metadataReader;
					for(Resource resource : resources){
						if(resource.isReadable()){
							metadataReader =  metadataReaderFactory.getMetadataReader(resource);
							try {
								result.add(Class.forName(metadataReader.getClassMetadata().getClassName()).getPackage().getName());
							} catch (ClassNotFoundException e) {
								e.printStackTrace();
							}
						}
					}
				}
			}
			if(!result.isEmpty()) {
				super.setTypeAliasesPackage(String.join( ",",result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
