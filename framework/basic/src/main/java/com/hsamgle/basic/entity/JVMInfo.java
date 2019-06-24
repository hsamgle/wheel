package com.hsamgle.basic.entity;

import lombok.Data;

import java.io.Serializable;


/**
 *
 *  @feture   :	    TODO		JVM 信息
 *	@file_name:	    JVMInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:13
 *	@company:		江南皮革厂
 */
@Data
public class JVMInfo implements Serializable {

    /** 操作系统信息 */
    private OSInfo    osInfo;

    /** 堆内存信息 */
    private HeapMemory heapMemory;

    /** 非堆内存信息 */
    private NonHeapMemory nonHeapMemory;

    /** 内存信息 */
    private MemoryInfo memoryInfo;

    /** 线程信息 */
    private ThreadInfo threadInfo;


}
