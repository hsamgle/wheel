package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Id;
import java.sql.Timestamp;


/**
 *
 *  @feture   :	    TODO	Mysql实体父类
 *	@file_name:	    MysqlEntity.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:14
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "id")
public class MysqlEntity extends DataEntity {


    /** 自增主键  */
    @Id
    @Column(name = "id")
    private Object id;

    /** 创建时间 秒*/
    @Column(name = "create_time")
    private  Timestamp createTime;

    /** 更新时间  秒*/
    @Column(name = "update_time")
    private  Timestamp updateTime;

    /** 数据状态 通用表示  -1 已删除  0 未生效  1 已生效 */
    private Integer state;
}
