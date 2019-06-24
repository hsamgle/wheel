package com.hsamgle.basic.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;


/**
 *
 *  @feture   :	    TODO		堆内存
 *	@file_name:	    HeapMemory.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:12
 *	@company:		江南皮革厂
 */
@EqualsAndHashCode(callSuper = false)
public class HeapMemory extends MemoryInfo{

    /** 标题 */
   @Getter private String title = "heap memory info";

}
