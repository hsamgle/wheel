package com.hsamgle.basic.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

/**
 *
 *  @feture   :	    TODO    令牌信息
 *	@file_name:	    TokenInfo.java
 * 	@packge:	    com.hsamgle.basic.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:15
 *	@company:		江南皮革厂
 */
@Data
@EqualsAndHashCode(callSuper = false,of = "token")
public  class TokenInfo extends SEntity{

	/** token类型 */
	protected String tag;

    /** token 标识 */
    private String token;

    /** 用户id */
    private Object userId;

    /** token 的创建时间 */
    private long createTime;

    /** 是否是超级管理员 */
	private boolean superAdmin;

	/** 如果是小程序端用户，则可以的免去权限校验通过某些特定接口 */
	private boolean wechatUser;

	/** 权限许可列表 */
	private Set<License> licenses;
}
