package com.hsamgle.basic.config;

import com.google.gson.GsonBuilder;
import com.hsamgle.basic.annotation.ValidateParamResolver;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;



/**
 *
 *  @feture   :	    TODO		web相关配置
 *	@file_name:	    WebConfig.java
 * 	@packge:	    com.hsamgle.config
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:07
 *	@company:		江南皮革厂
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

      @Override
      public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
    	  // 启动的参数校验自定义注解
          argumentResolvers.add(new ValidateParamResolver());
      }
      


  	 /**
  	   *
  	   * @method:	TODO    添加这个过滤器是为了解决 spring boot 中文乱码的问题
  	   * @time  :	2018/3/27 9:07
  	   * @author:	黄鹤老板
  	   * @param
  	   * @return:
  	   */
      @Bean
      public Filter characterEncodingFilter() {
          return new CharacterEncodingFilter("UTF-8");
      }


       /**
         *
         * @method:	TODO    返回Json 时自动忽略结果为NULL的字段
         * @time  :	2018/3/27 9:07
         * @author:	黄鹤老板
         * @param
         * @return:
         */
      @Bean
      public GsonHttpMessageConverter IngoreNUll(){
      	GsonHttpMessageConverter converter = new GsonHttpMessageConverter();
      	converter.setGson(new GsonBuilder().create());
      	List<MediaType> supportedMediaTypes = new ArrayList<>();
      	supportedMediaTypes.add(MediaType.parseMediaType("text/json;charset=UTF-8"));
      	supportedMediaTypes.add(MediaType.parseMediaType("application/json;charset=UTF-8"));
      	converter.setSupportedMediaTypes(supportedMediaTypes);
      	return converter;
      }

	/**
	 *
	 * @method:	TODO	目的是解决URL有非法字符被拦截的问题
	 * @time  :	2018/12/6 22:20
	 * @author:	黄鹤老板
	 * @param
	 * @return:     org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
	 */
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {

		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers((TomcatConnectorCustomizer) connector -> connector.setProperty("relaxedQueryChars", "|{}[]"));
		return factory;
	}
}
