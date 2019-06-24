package com.hsamgle.basic.utils;


import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import com.hsamgle.basic.entity.LoggerInfo;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;



/**
 *
 *  @feture   :	    TODO	 日志记录工具
 *	@file_name:	    LogUtil.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:42
 *	@company:		江南皮革厂
 */
public final class LogUtil {

    private static final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    /** 是否允许Sytem.out.print输出 */
    public static boolean CONSOLE_VIABLE = true;


    /**
     *
     *  @feture   :	    TODO		更改级别
     *	@file_name:	    LogUtil.java
     * 	@packge:	    com.hsamgle.basic.utils
     *	@author:	    黄鹤老板
     *  @create_time:	2018/3/27 9:43
     *	@company:		江南皮革厂
     */
    public static LoggerInfo changeLevel(String className, String level) throws Exception {
        Level lev;
        switch (level) {
            case "ALL":
                lev = Level.ALL;
                break;
            case "DEBUG":
                lev = Level.DEBUG;
                break;
            case "INFO":
                lev = Level.INFO;
                break;
            case "WARN":
                lev = Level.WARN;
                break;
            case "ERROR":
                lev = Level.ERROR;
                break;
            case "OFF":
                lev = Level.OFF;
                break;
            case "TRACE":
                lev = Level.TRACE;
                break;
            case "CONSOLE":
                lev = Level.INFO;
                break;
            default:
                lev = Level.INFO;
                break;
        }
        // 如果没有设置pakage name或class name，则改变整个root的级别
        Logger logger;
        if (StringUtils.isEmpty(className)) {
            logger = loggerContext.getLogger(Logger.ROOT_LOGGER_NAME);
        } else {
            logger = loggerContext.getLogger(className);
        }
        logger.setLevel(lev);

        return new LoggerInfo(logger.getName(), logger.getLevel().levelStr);
    }


    /**
     *
     * @method:	TODO    获取系统中所有的logo信息
     * @time  :	2018/3/27 9:44
     * @author:	黄鹤老板
     * @param
     * @return:     java.util.List<com.hsamgle.basic.entity.LoggerInfo>
     */
    public static List<LoggerInfo> getLoggers() {
        List<LoggerInfo> loggers = new ArrayList<>();
        loggerContext.getLoggerList().forEach(logger -> {
            if (logger.getLevel() != null) {
                loggers.add(new LoggerInfo(logger.getName(), logger.getLevel().levelStr));
            }
        });
        return loggers;
    }


    /**
     *
     * @method:	TODO    在控制台输出日志
     * @time  :	2018/3/27 9:44
     * @author:	黄鹤老板
     * @param msg
     * @return:     void
     */
    public static void console(String msg) {
        if (CONSOLE_VIABLE) {
            System.out.println(DateTimeUtils.getTime() + "  " + msg);
        }
    }


    /**
     *
     * @method:	TODO     Debug 级别,需要将记录添加到DEBUG.log 文件中
     * @time  :	2018/3/27 9:44
     * @author:	黄鹤老板
     * @param debug
     * @return:     void
     */
    public static void debug(String debug) {
        getLogger().debug(debug);
    }

    /**
     *
     * @method:	TODO    Error 级别的话，需要将错误的信息添加到的Error.log文件中
     * @time  :	2018/3/27 9:44
     * @author:	黄鹤老板
     * @param error
     * @return:     void
     */
    public static void error(String error) {
        getLogger().error(error);
    }

    public static void error(Throwable throwable) {
        getLogger().error(throwable.getMessage(), throwable);
    }

    public static void error(String message, Throwable throwable) {
        getLogger().error(message, throwable);
    }

    /**
     *
     * @method:	TODO    Info 级别，需要将记录添加到INFO.log 文件中
     * @time  :	2018/3/27 9:45
     * @author:	黄鹤老板
     * @param info
     * @return:     void
     */
    public static void info(String info) {
        getLogger().info(info);
    }

    private static Logger getLogger() {
        Logger logger = null;
        try {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            String className = stackTraceElement.getClassName();
            logger = loggerContext.getLogger(Class.forName(className));
            if (logger.getLevel() == null) {
                logger.setLevel(Level.INFO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return logger;
    }

}
