package com.hsamgle.basic.juhe.entity;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.SEntity;
import lombok.Getter;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.juhe.entity
 * @ClassName
 * @Description
 * @date 2018/11/8 15:48
 * @company 广州讯动网络科技有限公司
 */
@Getter
public class SMSInfo extends SEntity {

	private Integer code;

	private String msg;

	public SMSInfo() {
		this.code = Code.SUCCESS;
		this.msg = "请求成功";
	}

	public SMSInfo(Integer code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	/** 发送数量 */
	private Integer count;

	/** 扣除条数 */
	private Integer fee;

	/** 短信的id */
	private String sid;
}
