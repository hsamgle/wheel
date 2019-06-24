package com.hsamgle.basic.utils;

import org.apache.commons.codec.binary.Base64;

/**
 *
 *  @feture   :	    TODO	base64 工具类
 *	@file_name:	    Base64Util.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:34
 *	@company:		江南皮革厂
 */
public final class Base64Util {
	/**
     * @param bytes
     * @return
     */
    public static byte[] decode(final byte[] bytes) {
        return Base64.decodeBase64(bytes);
    }

    /**
     * 二进制数据编码为BASE64字符串
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(final byte[] bytes) {
        return new String(Base64.encodeBase64(bytes));
    }

}
