package com.hsamgle.basic.juhe;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.RequestEntity;
import com.hsamgle.basic.http.HttpExecutor;
import com.hsamgle.basic.juhe.entity.ExpInfo;
import com.hsamgle.basic.juhe.entity.JuheResult;
import com.hsamgle.basic.utils.JsonUtil;
import org.springframework.util.Assert;

/**
 *
 *  @feture   :	    TODO	使用聚合数据查询快递信息的工具类
 *	@file_name:	    ExpUtils.java
 * 	@packge:	    com.hsamgle.basic.juhe
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/8 14:43
 *	@company:		江南皮革厂
 */
public final class ExpUtils {


	private static final String HOST = "http://v.juhe.cn/exp/index";

	/**
	 *
	 * @method:	TODO    根据单号来查询快递信息
	 * @time  :	2018/11/8 15:25
	 * @author:	黄鹤老板
	 * @param no
	 * @return:     com.hsamgle.basic.juhe.entity.ExpInfo
	 */
	public static ExpInfo trace(String no,String code) throws Exception {

		Assert.isTrue(!"null".equals(JuheConfig.ExpKey),"需要配置快递应用的key");
		Assert.isTrue(no!=null,"快递单号不能为空");
		Assert.isTrue(code!=null,"需要提供快递公司编码");

		RequestEntity entity = new RequestEntity(HOST);
		entity.addQuery("key",JuheConfig.ExpKey);
		entity.addQuery("com",code);
		entity.addQuery("no",no);

		String execute = HttpExecutor.execute(entity);
		JuheResult juhe = JsonUtil.toObject(execute, JuheResult.class);
		if(juhe!=null && "200".equals(juhe.getResultcode())){
			// 调用成功
			Object result = juhe.getResult();
			return JsonUtil.toObject(JsonUtil.toJson(result),ExpInfo.class);
		}
		return new ExpInfo(Code.TP_API_INVOKE_ERR,Code.TP_API_INVOKE_ERR_MSG+(juhe==null?"":juhe.getReason()));
	}

}
