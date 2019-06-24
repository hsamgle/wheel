package com.hsamgle.basic.utils;

import org.apache.http.util.TextUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 *
 *  @feture   :	    TODO	时间工具类
 *	@file_name:	    DateTimeUtils.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:34
 *	@company:		江南皮革厂
 */
public class DateTimeUtils {


    /**
     * @param
     * @method: TODO 获取当前时间并格式化  yyyy-MM-dd HH:mm:ss
     * @time :	2018/3/27 9:35
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String getTime ( ) {

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return df.format(new Date());
    }

    /**
     * @param time
     * @method: TODO 将已经格式化 yyyy-MM-dd 的时间转为long类型
     * @time :	2018/3/27 9:35
     * @author: 黄鹤老板
     * @return: long
     */
    public static long string2Long ( String time ) throws ParseException {

        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
        sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = sdf1.parse(time);
        return date.getTime();
    }


    /**
     * @param time
     * @param format
     * @method: TODO 将已经格式化的时间转为long类型
     * @time :	2018/3/27 9:35
     * @author: 黄鹤老板
     * @return: long
     */
    public static long string2Long ( String time, String format ) throws ParseException {
        SimpleDateFormat sdf1 = new SimpleDateFormat(format);
        sdf1.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        Date date = sdf1.parse(time);
        return date.getTime();
    }


    /**
     * @param format
     * @method: TODO 获取当前时间的指定格式化字符串
     * @time :	2018/3/27 9:36
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String getSimpleDateTime ( String format ) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(System.currentTimeMillis());
    }


    /**
     * @param time
     * @method: TODO long 转 date
     * @time :	2018/3/27 9:36
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date long2Date ( long time ) {
        return new Date(time);
    }

    /**
     * @param dateTime
     * @param format
     * @method: TODO date类型时间转为某种格式的字符串时间
     * @time :	2018/3/27 9:37
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String date2FormatString ( Date dateTime, String format ) {
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        formatter.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        return formatter.format(dateTime);
    }


    /**
     * @param time
     * @param format
     * @method: TODO long类型的时间转为某种格式字符串的时间
     * @time :	2018/3/27 9:37
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String long2FormatString ( long time, String format ) {
        return date2FormatString(long2Date(time), format);
    }

    /**
     * @param dateTime
     * @param format
     * @method: TODO 将字符串转为按某规则定义的Date
     * @time :	2018/3/27 9:37
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date String2FormatDate ( String dateTime, String format ) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.parse(dateTime);
    }


    /**
     * @param intervalTime
     * @method: TODO 由秒转为几日几天计时几分几秒的工具
     * @time :	2018/3/27 9:37
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String getIntervalTime ( long intervalTime ) {

        StringBuilder result = new StringBuilder();
        long interval = intervalTime / 1000;
        final long day = 24 * 60 * 60;
        final long hour = 60 * 60;
        final long minute = 60;
        int detailDay = 0;
        int detailHour = 0;
        int detailMinute = 0;
        int detailSecond = 0;
        if ( interval >= day ) {
            detailDay = (int) ( interval / day );
            interval = interval - detailDay * day;
        }
        if ( interval >= hour ) {
            detailHour = (int) ( interval / hour );
            interval = interval - hour * detailHour;
        }
        if ( interval >= minute ) {
            detailMinute = (int) ( interval / minute );
            interval = interval - detailMinute * minute;
        }
        if ( interval > 0 ) {
            detailSecond = (int) interval;
        }
        result.setLength(0);
        if ( detailDay > 0 ) {
            result.append(detailDay);
            result.append("天");
        }
        if ( detailHour > 0 ) {
            result.append(detailHour);
            result.append("小时");
        }
        if ( detailMinute > 0 ) {
            result.append(detailMinute);
            result.append("分");
        }
        if ( detailSecond > 0 ) {
            result.append(detailSecond);
            result.append("秒");
        }
        return result.toString();
    }


    /**
     * @param
     * @method: TODO 获取当前的年份
     * @time :	2018/3/27 9:37
     * @author: 黄鹤老板
     * @return: int
     */
    public static int getYear ( ) {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.YEAR);
    }


    /**
     * @param
     * @method: TODO 获取当前的月份
     * @time :	2018/3/27 9:38
     * @author: 黄鹤老板
     * @return: int
     */
    public static int getMouth ( ) {
        Calendar a = Calendar.getInstance();
        //由于月份是从0开始的所以加1
        return a.get(Calendar.MONTH) + 1;
    }


    /**
     * @param
     * @method: TODO 获取当前是几号
     * @time :	2018/3/27 9:38
     * @author: 黄鹤老板
     * @return: int
     */
    public static int getDate ( ) {
        Calendar a = Calendar.getInstance();
        return a.get(Calendar.DATE);
    }


    /**
     * @param
     * @method: TODO 获取当前系统的时间，以秒返回
     * @time :	2018/3/27 9:38
     * @author: 黄鹤老板
     * @return: int
     */
    public static int getNowInSecond ( ) {
        return Integer.valueOf(( System.currentTimeMillis() / 1000 ) + "");
    }


    /**
     * @param intervalTime
     * @method: TODO 相隔的天数
     * @time :	2018/3/27 9:39
     * @author: 黄鹤老板
     * @return: int
     */
    public static int getIntervalDay ( long intervalTime ) {

        long interval = intervalTime / 1000;
        final long day = 24 * 60 * 60;
        int detailDay = 0;
        if ( interval >= day ) {
            detailDay = (int) ( interval / day );
        }
        return detailDay;
    }

    /**
     * 获取毫秒，设置时区
     */
    public static Long getNowInMillis ( ) {
        TimeZone.getTimeZone("Asia/Shanghai");
        return System.currentTimeMillis();
    }

    /**
     * @param
     * @method: TODO 获取Timestamp
     * @time :	2018/3/27 9:39
     * @author: 黄鹤老板
     * @return: java.sql.Timestamp
     */
    public static Timestamp getTimestamp ( ) {
        return new Timestamp(System.currentTimeMillis());
    }


    public static Long ONE_DAY = 1000 * 60 * 60 * 24L;

    /**
     * @param endTime
     * @return java.util.List<java.lang.String>
     * @方法功能： TODO 获取连个时间间隔的日期
     * @编写时间： 2018/3/27 9:40
     * @author： 黄先国 | hsamgle@qq.com
     * * @param startTime
     */
    public static List<String> getDateBetween ( String startTime, String endTime ) throws ParseException {

        Long start = string2Long(startTime);

        Long end = string2Long(endTime);

        List<String> dates = new ArrayList<>();

        while ( start <= end ) {
            Date d = new Date(start);
            DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
            dates.add(df.format(d));
            start += ONE_DAY;
        }
        return dates;
    }


    /**
     * @param
     * @method: TODO 获取当天的开始时间
     * @time :	2018/3/27 9:40
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getDayBegin ( ) {

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * @param
     * @method: TODO 获取当天的结束时间
     * @time :	2018/3/27 9:40
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getDayEnd ( ) {

        Calendar cal = new GregorianCalendar();
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    /**
     * @param
     * @method: TODO 获取昨天的开始时间
     * @time :	2018/3/27 9:40
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getBeginDayOfYesterday ( ) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayBegin());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * @param
     * @method: TODO 获取昨天的结束时间
     * @time :	2018/3/27 9:40
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getEndDayOfYesterDay ( ) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(getDayEnd());
        cal.add(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     * @param format
     * @method: TODO 获取明天日期字符
     * @time :	2018/3/27 9:40
     * @author: 黄鹤老板
     * @return: java.lang.String
     */
    public static String getYesterDay ( String format ) {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        DateFormat df = new SimpleDateFormat(format);
        return df.format(cal.getTime());
    }


    /**
     * @param date
     * @method: TODO 获取某一天的开始时间
     * @time :	2018/3/27 9:41
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getDayBegin ( Date date ) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }


    /**
     * @param date
     * @method: TODO 获取某一天的结束时间
     * @time :	2018/3/27 9:41
     * @author: 黄鹤老板
     * @return: java.util.Date
     */
    public static Date getDayEnd ( Date date ) {

        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static String getDateToString(Date date) {
        SimpleDateFormat format;
        String hintDate = "";
        //先获取年份
        int year = Integer.valueOf(new SimpleDateFormat("yyyy").format(date));
        //获取一年中的第几天
        int day = Integer.valueOf(new SimpleDateFormat("d").format(date));
        //获取当前年份 和 一年中的第几天
        Date currentDate = new Date(System.currentTimeMillis());
        int currentYear = Integer.valueOf(new SimpleDateFormat("yyyy").format(currentDate));
        int currentDay = Integer.valueOf(new SimpleDateFormat("d").format(currentDate));
        //计算 如果是去年的
        if (currentYear - year == 1) {
            //如果当前正好是 1月1日 计算去年有多少天，指定时间是否是一年中的最后一天
            if (currentDay == 1) {
                int yearDay;
                if (year % 400 == 0) {
                    //世纪闰年
                    yearDay = 366;
                } else if (year % 4 == 0 && year % 100 != 0) {
                    //普通闰年
                    yearDay = 366;
                } else {
                    //平年
                    yearDay = 365;
                }
                if (day == yearDay) {
                    hintDate = "昨天";
                }
            }
        } else {
            if (currentDay - day == 1) {
                hintDate = "昨天";
            }
            if (currentDay - day == 2) {
                hintDate = "前天";
            }
            if (currentDay - day == 0) {
                hintDate = "今天";
            }
            if(currentDay - day == -1){
                hintDate = "明天";
            }
            if(currentDay - day == -2){
                hintDate = "后天";
            }
        }
        if ( TextUtils.isEmpty(hintDate)) {
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            return format.format(date);
        } else {
            format = new SimpleDateFormat("HH:mm");
            return hintDate + " " + format.format(date);
        }

    }
}
