package com.hsamgle.mysql.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName cn.com.dplus.mongodb.utils
 * @ClassName
 * @Description
 * @date 2018/5/9 17:29
 * @company 广州讯动网络科技有限公司
 */
public final class FieldUtils {


	/**
	 *
	 * @方法功能：	TODO    获取一个实体的所有属性
	 * @编写时间：	2018/5/9 17:30
	 * @author：	黄先国 | hsamgle@qq.com
	 * * @param obj
	 * @return     java.util.List<java.lang.reflect.Field>
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

	private static final Pattern PATTERN = Pattern.compile("[A-Z]");
	public   static String switchCase(String field) {
		if (field.matches("[a-z]+[A-Z][a-z]+([A-Z][a-z]+)*")){
			Matcher matcher = PATTERN.matcher(field);
			while(matcher.find()){
				String old = matcher.group();
				String ne = old.toLowerCase();
				field = field.replaceAll(old, "_"+ne);
			}
		}
		return field;
	}

}
