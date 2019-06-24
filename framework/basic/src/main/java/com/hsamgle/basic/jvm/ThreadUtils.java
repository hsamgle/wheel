package com.hsamgle.basic.jvm;

import com.hsamgle.basic.entity.ThreadInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;


/**
 *
 *  @feture   :	    TODO		JVM线程相关工具类
 *	@file_name:	    ThreadUtils.java
 * 	@packge:	    com.hsamgle.jvm
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:24
 *	@company:		江南皮革厂
 */
 public final class ThreadUtils {



    /**
     *
     * @method:	TODO     获取当前的线程的信息
     * @time  :	2018/3/27 9:24
     * @author:	黄鹤老板
     * @param
     * @return:     ThreadInfo
     */
     public static ThreadInfo getThreadInfo(){

        try {
            ThreadMXBean threadMBean = ManagementFactory.getThreadMXBean();
            if(threadMBean!=null){
                ThreadInfo threadInfo = new ThreadInfo();
                threadInfo.setCurrentThreadCpuTime(threadMBean.getCurrentThreadCpuTime());
                threadInfo.setCurrentThreadUserTime(threadMBean.getCurrentThreadUserTime());
                threadInfo.setDaemonThreadCount(threadMBean.getDaemonThreadCount());
                threadInfo.setPeakThreadCount(threadMBean.getPeakThreadCount());
                threadInfo.setThreadCount(threadMBean.getThreadCount());
                return threadInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }

}
