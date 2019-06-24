package com.hsamgle.basic.constant;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.constant
 * @ClassName
 * @Description
 * @date 2018/5/26 12:11
 * @company 广州讯动网络科技有限公司
 */
public enum SecurityLevel {

	/** 绿色通行 */
	PASS(0),
	/** token有效即可 */
	NORMAL(1),
	/** 需要参数签名  */
	SIGNATURE(2),
	/** 需要校验权限  */
	PERMISSION(3),
	/** 提供给小程序调用的 */
	WECHAT(4);

	SecurityLevel(int level) {
		this.level = level;
	}
	public int level;
}
