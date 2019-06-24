package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * 
 *  @feture   :	    TODO	非堆内存
 *	@file_name:	    NonHeapMemory.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/3/27 9:14
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class NonHeapMemory extends MemoryInfo{

    /** 标题 */
    private String title = "non heap memory info";

}
