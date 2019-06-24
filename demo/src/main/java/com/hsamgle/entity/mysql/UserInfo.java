package com.hsamgle.entity.mysql;

import com.hsamgle.basic.entity.MysqlEntity;
import com.hsamgle.mysql.annotation.PK;
import com.hsamgle.mysql.constant.PKType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Table;

/**
 *
 *  @feture   :	    TODO	用户实体
 *	@file_name:	    UserInfo.java
 * 	@packge:	    com.hsamgle.entity.mysql
 *	@author:	    黄鹤老板
 *  @create_time:	2018/10/30 11:38
 *	@company:		江南皮革厂
 */
@Data                                   // lombok 注解 自动生成getter和setter相关代码
@EqualsAndHashCode(callSuper = false)  // lombok 注解 是否使用父类的hashCode 和 equals方法
@Table(name = "user")                   // 映射数据库表名
@PK(generator = PKType.UUID)            // 这里指定了数据主键的生成规则    详见 PKTpye说明
public class UserInfo extends MysqlEntity {   // 继承父类的意义是 自动获取   id  , createTime, updateTime 属性的自动管理

    /** 用户名称 */
    @Column(name = "user_name")   // 映射别名
    private String userName;

    @Column(name = "gender")
    private Integer gender;

    /** 属性名称跟表字段名称一致时可以不需要指定别名 */
    private Integer age;



}
