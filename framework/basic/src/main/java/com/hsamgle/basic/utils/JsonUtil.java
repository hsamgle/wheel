package com.hsamgle.basic.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;


/**
 *
 *  @feture   :	    TODO		json工具类
 *	@file_name:	    JsonUtil.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:41
 *	@company:		江南皮革厂
 */
public class JsonUtil {


    private final static Gson gson = new GsonBuilder().disableHtmlEscaping().create();

    private final static Gson prettyGson = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();


    /**
     *
     * @method:	TODO    将对象转换为json数据
     * @time  :	2018/3/27 9:42
     * @author:	黄鹤老板
     * @param object
     * @return:     java.lang.String
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static String toPrettyJson(Object object) {
        return prettyGson.toJson(object);
    }

    /**
     *
     * @method:	TODO    json 转换为对象
     * @time  :	2018/3/27 9:42
     * @author:	黄鹤老板
     * @param json
    * @param t
     * @return:     T
     */
    public static <T> T toObject(String json, Class<T> t) {

        try {
            return gson.fromJson(json, t);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @method:	TODO    通过类型反射的方式解释json数据
     * @time  :	2018/3/27 9:42
     * @author:	黄鹤老板
     * @param json
    * @param type
     * @return:     T
     */
    public static <T> T toObject(String json, Type type) {
        return gson.fromJson(json, type);
    }


}
