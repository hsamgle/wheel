package com.hsamgle.basic.utils;

import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



/**
 * 
 *  @feture   :	    TODO		MD5加密工具
 *	@file_name:	    MD5Util.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/3/27 9:45
 *	@company:		江南皮革厂
 */
public final class MD5Util {

	/** 全局数组 */
	private final static String[] DIGITS = { "0", "1", "2", "3", "4", "5",
			"6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

	public MD5Util() {
	}

	// 返回形式为数字跟字符串
	private static String byteToArrayString(byte bByte) {
		int iRet = bByte;
		// System.out.println("iRet="+iRet);
		if (iRet < 0) {
			iRet += 256;
		}
		int iD1 = iRet / 16;
		int iD2 = iRet % 16;
		return DIGITS[iD1] + DIGITS[iD2];
	}

	// 返回形式只为数字
	@SuppressWarnings("unused")
	private static String byteToNum(byte bByte) {
		int iRet = bByte;
		if (iRet < 0) {
			iRet += 256;
		}
		return String.valueOf(iRet);
	}

	// 转换字节数组为16进制字串
	private static String byteToString(byte[] bByte) {
		StringBuilder builder = new StringBuilder();
		for (byte b : bByte) {
			builder.append(byteToArrayString(b));
		}
		return builder.toString();
	}

	//对字符串进行MD5加密
	public static String GetMD5Code(String strObj) {
		String resultString = null;
		try {
			resultString = strObj;
			MessageDigest md = MessageDigest.getInstance("MD5");
			// md.digest() 该函数返回值为存放哈希值结果的byte数组
			resultString = byteToString(md.digest(strObj.getBytes()));
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		}
		return resultString;
	}


	public static String getMD5(String st) { 
	       String s = null;  
	       char hexDigits[] = { // 用来将字节转换成 16 进制表示的字符  
	           '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};  
	       try {  
	       	byte[] source = st.getBytes("UTF-8");
	           java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");  
	           md.update(source);  
	           byte tmp[] = md.digest();          // MD5 的计算结果是一个 128 位的长整数，  
	           // 用字节表示就是 16 个字节  
	           char str[] = new char[16 * 2];   // 每个字节用 16 进制表示的话，使用两个字符，  
	           // 所以表示成 16 进制需要 32 个字符  
	           int k = 0;                                // 表示转换结果中对应的字符位置  
	           for (int i = 0; i < 16; i++) {    // 从第一个字节开始，对 MD5 的每一个字节  
	               // 转换成 16 进制字符的转换  
	               byte byte0 = tmp[i];  // 取第 i 个字节  
	               str[k++] = hexDigits[byte0 >>> 4 & 0xf];  // 取字节中高 4 位的数字转换,  
	               // >>> 为逻辑右移，将符号位一起右移  
	               str[k++] = hexDigits[byte0 & 0xf];   // 取字节中低 4 位的数字转换  
	           }  
	           s = new String(str);  // 换后的结果转换为字符串  
	  
	       } catch (Exception e) {  
	           e.printStackTrace();  
	       }  
	       return s;
	}
	
	/**
	 * 获取文件的MD5
	 */
	public static String getFileMD5(MultipartFile file) throws Exception {
	    byte[] uploadBytes = file.getBytes();
	    MessageDigest md5 = MessageDigest.getInstance("MD5");
	    byte[] digest = md5.digest(uploadBytes);
	    String hashString = new BigInteger(1, digest).toString(16);
	    return hashString.toUpperCase();
	}
}
