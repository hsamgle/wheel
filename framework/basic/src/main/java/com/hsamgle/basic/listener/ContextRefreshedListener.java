package com.hsamgle.basic.listener;

import com.hsamgle.basic.annotation.Token;
import com.hsamgle.basic.entity.TokenInfo;
import com.hsamgle.basic.security.TokenManager;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ContextRefreshedListener implements ApplicationListener<ContextRefreshedEvent> {

	private static boolean checked =false;

    @Override
    @SuppressWarnings(value = "unchecked")
    public void onApplicationEvent(ContextRefreshedEvent event) {  
        // 根容器为Spring容器  
        if(event.getApplicationContext().getParent()==null && !checked){
            Map<String,Object> beans = event.getApplicationContext().getBeansWithAnnotation(Token.class);
            for(Object bean:beans.values()){
	            Class<? extends TokenInfo> beanClass = (Class<? extends TokenInfo>) bean.getClass();
	            Token token = beanClass.getAnnotation(Token.class);
	            TokenManager.register(token.tag(), beanClass);
            }
	        checked = true;
        }  
    }  
}