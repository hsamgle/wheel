package com.hsamgle.mysql.constant;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName cn.com.dplus.mysql
 * @ClassName
 * @Description
 * @date 2018/8/15 13:49
 * @company 广州讯动网络科技有限公司
 */
public enum PKType {

	/** 返回 uuid 类型主键 */
	UUID,
	/** 返回 long 类型 顺序主键 */
	ORDER,
	/** 返回 自增 类型主键，自增类型话需要数据库设置该字段为自增类型 */
	AUTO_INC,
	/** 返回 时间戳·毫秒 类型主键 */
	Second,
	/** 返回 时间戳·毫秒 类型主键 */
	MilliSecond,
	/** 返回 时间戳·呐秒 类型主键 */
	NanoSecond
}
