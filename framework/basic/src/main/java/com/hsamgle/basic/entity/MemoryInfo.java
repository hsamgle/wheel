package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 *  @feture   :	    TODO		JVM内存相关信息
 *	@file_name:	    MemoryInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:13
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class MemoryInfo extends DataEntity {

    /** 初始化大小 */
    private Long init;

    /** 最大内存  */
    private Long max;

    /** 已提交内存 */
    private Long committed;

    /** 已使用的内存  */
    private Long used;

    /** JVM 内存总量  */
    private Long jvmTotalMemory;

    /** JVM 空闲内存量  */
    private Long jvmFreeMemory;

    /** JVM 最大内存量  */
    private Long jvmMaxMemory;

}
