package com.hsamgle.basic.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 *  @feture   :	    TODO    实体反射工具类
 *	@file_name:	    FieldUtils.java
 * 	@packge:	    com.hsamgle.mongodb.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:38
 *	@company:		江南皮革厂
 */
public final class FieldUtils {


	/**
	 *
	 * @method:	TODO    获取一个实体的所有属性
	 * @time  :	2018/5/21 9:39
	 * @author:	黄鹤老板
	 * @param obj
	 * @return:     java.util.List<java.lang.reflect.Field>
	 */
	public  static List<Field> getAllFields(Class<?> obj){

		List<Field> allFields = new ArrayList<>();

		Field[] declaredFields = obj.getDeclaredFields();
		Class<?> superclass = obj.getSuperclass();
		Field[] superFields = superclass !=null?superclass.getDeclaredFields():null;
		Field[] fields = obj.getFields();

		if(declaredFields!=null){
			allFields.addAll(Arrays.asList(declaredFields));
		}
		if(superFields!=null){
			allFields.addAll(Arrays.asList(superFields));
		}
		if(fields!=null){
			allFields.addAll(Arrays.asList(fields));
		}
		return allFields;
	}

}
