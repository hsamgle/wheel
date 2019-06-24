package com.hsamgle.basic.juhe.entity;

import com.hsamgle.basic.entity.SEntity;
import lombok.Getter;

/**
 * @author 黄先国 | huangxg@sondon.net
 * @PackageName com.hsamgle.basic.juhe.entity
 * @ClassName
 * @Description
 * @date 2018/11/8 15:45
 * @company 广州讯动网络科技有限公司
 */
@Getter
public class JuheResult extends SEntity{

	/** 老版状态码，新用户请忽略此字段 */
	private String resultcode;

	/** 接口调用查询信息 */
	private String reason;

	/** 查询的结果 */
	private Object result;

	/** 异常码 */
	private Integer errpr_code;


}
