package com.hsamgle.basic.utils;

import com.hsamgle.basic.entity.OSInfo;
import com.hsamgle.basic.exception.ThreadPoolException;
import com.hsamgle.basic.jvm.OSUtils;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;

import java.util.concurrent.*;


/**
 *
 *  @feture   :	    TODO	线程池工具类
 *	@file_name:	    ThreadPoolExecutors.java
 * 	@packge:	    com.hsamgle.basic.utils
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:48
 *	@company:		江南皮革厂
 */
public final class ThreadPoolExecutors {

    private static ThreadPoolExecutor threadPool = null;


    /**  获取系统的信息 */
    private static OSInfo osInfo = OSUtils.getOSInfo();

    /**
     *
     * @method:	TODO   执行初始化线程池
     * @time  :	2018/3/27 9:50
     * @author:	黄鹤老板
     * @param
     * @return:     boolean
     */
    public static boolean init(){

        try {
            if(threadPool==null){
                synchronized (ThreadPoolExecutor.class){
                    if(threadPool==null){
                        LogUtil.info("正在初始化系统线程池");
                        int processors = osInfo.getProcessors();
                        int corePoolSize = processors*2;
                        int maximumPoolSize = processors*4;
                        long keepAliveTime = 0L;
                        BasicThreadFactory factory = new BasicThreadFactory
                                .Builder()
                                .namingPattern("demo-pool-%d")
                                .daemon(true)
                                .build();
                        threadPool = new ThreadPoolExecutor(
                                corePoolSize,
                                maximumPoolSize,
                                keepAliveTime,
                                TimeUnit.SECONDS,
                                new LinkedBlockingQueue<>(maximumPoolSize*10),
                                factory,
                                new ThreadPoolExecutor.AbortPolicy());
                        LogUtil.info("初始化线程池信息\n" +
                                "CorePoolSize: "+ threadPool.getCorePoolSize()+
                                "\nMaximumPoolSize:  "+ threadPool.getMaximumPoolSize()+
                                "\nKeepAliveTime:  "+ threadPool.getKeepAliveTime(TimeUnit.SECONDS)
                        );
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     *
     * @method:	TODO    执行任务
     * @time  :	2018/3/27 9:50
     * @author:	黄鹤老板
     * @param runnable
     * @return:     boolean
     */
    public static boolean execute(Runnable runnable)throws ThreadPoolException {

        try {
            if(threadPool!=null || init()){
                threadPool.execute(runnable);
                return  true;
            }
        }catch (Exception e){
            throw  new ThreadPoolException("无法提交任务：  "+e.getMessage());
        }
        return false;
    }

    /**
     *
     * @method:	TODO     提交任务
     * @time  :	2018/3/27 9:50
     * @author:	黄鹤老板
     * @param task
     * @return:     java.util.concurrent.Future<T>
     */
    public static <T> Future<T> submit( Callable<T> task)throws ThreadPoolException{

        try {
            if(threadPool!=null || init()){
               return threadPool.submit(task);
            }
        }catch (Exception e){
            throw  new ThreadPoolException("无法提交任务：  "+e.getMessage());
        }
        return null;
    }

    /**
     *
     * @method:	TODO    创建定时任务线程池
     * @time  :	2018/3/27 9:50
     * @author:	黄鹤老板
     * @param
     * @return:     java.util.concurrent.ScheduledExecutorService
     */
    public static ScheduledExecutorService getScheduledExecutorService(){

        return new ScheduledThreadPoolExecutor(1,
                new BasicThreadFactory.Builder().namingPattern("schedule-pool-%d").daemon(true).build());
    }

    /**
     *
     * @method:	TODO    获取单线程线程池
     * @time  :	2018/3/27 9:50
     * @author:	黄鹤老板
     * @param
     * @return:     java.util.concurrent.ExecutorService
     */
    public static ExecutorService getSingleExecutorService(){
        return new ThreadPoolExecutor(1,
        1,
        0L,
        TimeUnit.SECONDS,
        new LinkedBlockingQueue<>(),
        new ThreadPoolExecutor.AbortPolicy());
    }

}
