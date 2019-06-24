package com.hsamgle.basic.utils;

import org.apache.commons.lang3.RandomStringUtils;



/**
 *
 *  @feture   :	    TODO	产生随机数的工具类
 *	@file_name:	    RandomUtil.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:46
 *	@company:		江南皮革厂
 */
public final class RandomUtil {


    /**
     *
     * @method:	TODO    产生 length 位的随机String类型的数字
     * @time  :	2018/3/27 9:46
     * @author:	黄鹤老板
     * @param length
     * @return:     java.lang.String
     */
	public static String getRanNUM(int length){
	    return RandomStringUtils.randomNumeric(length);
	}
	

    /**
     *
     * @method:	TODO    产生 长度为length 位的随机long类型的数字
     * @time  :	2018/3/27 9:47
     * @author:	黄鹤老板
     * @param length
     * @return:     long
     */
	public static long getRanLongNUM(int length){
	    return Long.valueOf(getRanNUM(length));
	}

    /**
     *
     * @method:	TODO    从指定的char集合中随机产生出长度为 length 的字符串
     * @time  :	2018/3/27 9:47
     * @author:	黄鹤老板
     * @param length
    * @param chars
     * @return:     java.lang.String
     */
	public static String getStringFromDefinedChar(int length,char... chars){
		return RandomStringUtils.random(length, chars);
	}

    /**
     *
     * @method:	TODO    随机获取长度为length的16进制的字符串
     * @time  :	2018/3/27 9:47
     * @author:	黄鹤老板
     * @param length
     * @return:     java.lang.String
     */
	public static String getHEXString(int length){
		char[] chars = new char[]{'1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		return getStringFromDefinedChar(length, chars);
	}


    /**
     *
     * @method:	TODO    获取长度为length的数字与字母的字符串
     * @time  :	2018/3/27 9:47
     * @author:	黄鹤老板
     * @param length
     * @return:     java.lang.String
     */
	public static String getNumAndAZ(int length){
	    return RandomStringUtils.randomAlphanumeric(length);
	}

    /**
     *
     * @method:	TODO   获取的a~z 包含大小写的字符串
     * @time  :	2018/3/27 9:48
     * @author:	黄鹤老板
     * @param length
     * @return:     java.lang.String
     */
	public static String getAz(int length){
	    return RandomStringUtils.randomAlphabetic(length);
	}



    /**
     *
     * @method:	TODO  生成从ASCII 32到126组成的随机字符串
     * @time  :	2018/3/27 9:48
     * @author:	黄鹤老板
     * @param length
     * @return:     java.lang.String
     */
	public static String getAsccii(int length){
	    return RandomStringUtils.randomAscii(length);
	}

	private static final char[] ENGS = new char[]{'a','b','c','d','e','f','g','h','i','j','k','l','m','n','o','p','q','r','s','t','u','v','w','x','y','z'};


    /**
     *
     * @method:	TODO    根据下标来获取英文字母
     * @time  :	2018/3/27 9:48
     * @author:	黄鹤老板
     * @param index
     * @return:     java.lang.String
     */
	public static String getENBYIndex(int index){

		if(index>-1 && index <26){
			return String.valueOf(ENGS[index]);
		}
		return null;
	}
}
