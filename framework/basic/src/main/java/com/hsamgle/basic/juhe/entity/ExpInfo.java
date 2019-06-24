package com.hsamgle.basic.juhe.entity;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.SEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.util.List;

/**
 *
 *  @feture   :	    TODO		查询快递信息实体
 *	@file_name:	    ExpInfo.java
 * 	@packge:	    com.hsamgle.basic.juhe.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/8 14:49
 *	@company:		江南皮革厂
 */
@Getter
@EqualsAndHashCode(callSuper = false)
public class ExpInfo extends SEntity {

	private Integer code;

	private String msg;

	public ExpInfo() {
		this.code = Code.SUCCESS;
		this.msg = "请求成功";
	}

	public ExpInfo(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/** 快递公司名称 */
	private String company;

	/** 快递公司代号 */
	private String com;

	/** 快递单号 */
	private String no;

	/** 1表示此快递单的物流信息不会发生变化，此时您可缓存下来；0表示有变化的可能性  */
	private String status;

	/**  详细的记录 */
	private List<Record> list;


	@Data
	public static class Record{

		/**   物流时间发生的时间  */
		private String datetime;

		/** 物流时间的描述 */
		private String remark;

		/**  当前快递所在区域，由于快递公司升级，现大多数快递不提供这个信息了  */
		private String zone;

	}


}
