package com.hsamgle.basic.utils;

import org.springframework.util.StringUtils;


/**
 *
 *  @feture   :	    TODO		提取中文的拼音首字母
 *	@file_name:	    FirstPYCharOfChinese.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:41
 *	@company:		江南皮革厂
 */
public final class FirstPYCharOfChinese {

    private final static int[] li_SecPosValue = { 1601, 1637, 1833, 2078, 2274,
            2302, 2433, 2594, 2787, 3106, 3212, 3472, 3635, 3722, 3730, 3858,
            4027, 4086, 4390, 4558, 4684, 4925, 5249, 5590 };

    private final static String[] lc_FirstLetter = { "A", "B", "C", "D", "E",
            "F", "G", "H", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S",
            "T", "W", "X", "Y", "Z" };

    /**
     * 取得给定汉字串的首字母串,即声母串
     *
     * @param str 给定汉字串
     * @return 声母串
     */
    public static String getAllFirstLetter ( String str ) {
        if ( StringUtils.isEmpty(str) ) {
            return "";
        }
        StringBuilder py = new StringBuilder();
        char[] chars = str.toCharArray();
        for ( char c : chars ) {
            py.append(getFirstLetter(c + ""));
        }
        return py.toString();
    }

    /**
     * 取得给定汉字的首字母,即声母
     *
     * @param chinese 给定的汉字
     * @return 给定汉字的声母
     */
    private static String getFirstLetter ( String chinese ) {
        try {
            chinese = new String(chinese.getBytes("GB2312"), "ISO8859-1");
            if ( chinese.length() > 1 ){
                // 判断是否为汉字
                int li_SectorCode = (int) chinese.charAt(0); // 汉字区码
                int li_PositionCode = (int) chinese.charAt(1); // 汉字位码
                li_SectorCode = li_SectorCode - 160;
                li_PositionCode = li_PositionCode - 160;
                int li_SecPosCode = li_SectorCode * 100 + li_PositionCode; // 汉字区位码
                if ( li_SecPosCode > 1600 && li_SecPosCode < 5590 ) {
                    for ( int i = 0; i < 23; i++ ) {
                        if ( li_SecPosCode >= li_SecPosValue[i] && li_SecPosCode < li_SecPosValue[i + 1] ) {
                            chinese = lc_FirstLetter[i];
                            break;
                        }
                    }
                } else{
                    // 非汉字字符,如图形符号或ASCII码
                    chinese = new String(chinese.getBytes("ISO8859-1"), "GB2312");
                    chinese = chinese.substring(0, 1);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return chinese;
    }

}