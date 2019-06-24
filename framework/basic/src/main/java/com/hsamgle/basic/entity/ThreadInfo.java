package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 *  @feture   :	    TODO		JVM线程信息
 *	@file_name:	    ThreadInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/3/27 9:15
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class ThreadInfo extends DataEntity {

    /** 线程数 */
    private int threadCount;

    /** 峰值线程数  */
    private int  peakThreadCount;

    /** 当前线程的cpu使用时间 */
    private long currentThreadCpuTime;

    /** 守护进程数  */
    private int daemonThreadCount;


    private long currentThreadUserTime;
}
