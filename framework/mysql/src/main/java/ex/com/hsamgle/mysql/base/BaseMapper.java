package ex.com.hsamgle.mysql.base;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 
 *  @param <T>
 * @类功能:	TODO		这里使用了特定的包名，防止被spring扫描到这个接口
 *	@文件名:	BaseMapper.java
 * 	@所在包:	ex.com.com.dplus.mysql.base
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2017年3月8日上午11:14:18
 *	@公_司:		广州讯动网络科技有限公司
 */
public interface BaseMapper<T> extends Mapper<T>, MySqlMapper<T>{
	
	
}
