package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 *
 *  @feture   :	    TODO	日志控制实体
 *	@file_name:	    LoggerInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:13
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "name")
public class LoggerInfo extends DataEntity {

    private String name;

    private String level;

    public LoggerInfo ( ) {
    }

    public LoggerInfo ( String name, String level ) {
        this.name = name;
        this.level = level;
    }
}
