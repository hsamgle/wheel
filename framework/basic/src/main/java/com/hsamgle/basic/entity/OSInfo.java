package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 *  @feture   :	    TODO		操作系统相关的信息
 *	@file_name:	    OSInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:14
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OSInfo extends DataEntity {


    /** 操作系统名称  */
    private String osName;

    /** 操作系统版本  */
    private String osVersion;

    /** 系统架构  */
    private String arch;

    /** 处理器的数据量  */
    private int processors;

}
