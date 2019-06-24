package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mongodb.morphia.annotations.Id;


/**
 *
 *  @feture   :	    TODO	mongo实体父类
 *	@file_name:	    MongoEntity.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:14
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "id")
public class MongoEntity extends DataEntity {

    @Id
    private String id;

    /** 记录的创建时间 */
    private Integer createTime;

    /** 记录的更新时间 */
    private Integer updateTime;

    /** 记录的状态   通常 -1 表示删除  */
    private Integer state;

}
