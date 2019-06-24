package com.hsamgle.basic.juhe;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.constant.Const;
import com.hsamgle.basic.entity.RequestEntity;
import com.hsamgle.basic.http.HttpExecutor;
import com.hsamgle.basic.juhe.entity.ExpInfo;
import com.hsamgle.basic.juhe.entity.JuheResult;
import com.hsamgle.basic.juhe.entity.SMSInfo;
import com.hsamgle.basic.utils.JsonUtil;
import org.springframework.util.Assert;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 *  @feture   :	    TODO	通过聚合数据第三方接口来完成短信的发送
 *	@file_name:	    SMSUtils.java
 * 	@packge:	    com.hsamgle.basic.juhe
 *	@author:	    黄鹤老板
 *  @create_time:	2018/11/8 15:42
 *	@company:		江南皮革厂
 */
public class SMSUtils {

	private static final String HOST = "http://v.juhe.cn/sms/send";


	/**
	 *
	 * @method:	TODO    发送短信
	 * @time  :	2018/11/8 15:56
	 * @author:	黄鹤老板
	 * @param mobile
	* @param tplId
	* @param tplValue
	 * @return:     com.hsamgle.basic.juhe.entity.SMSInfo
	 */
	public static SMSInfo send(String mobile,String tplId,String tplValue) throws Exception {

		Assert.isTrue(!"null".equals(JuheConfig.SMSKey),"需要配置短信应用的key");
		Assert.isTrue(mobile!=null,"目标手机号码为空");
		Assert.isTrue(tplId!=null,"需要指定短信模板id");

		RequestEntity entity = new RequestEntity(HOST);
		entity.addQuery("mobile",mobile);
		entity.addQuery("tpl_id",tplId);
		entity.addQuery("tpl_value", URLEncoder.encode(tplValue,"UTF-8"));
		entity.addQuery("key",JuheConfig.SMSKey);

		String execute = HttpExecutor.execute(entity);
		JuheResult juhe = JsonUtil.toObject(execute, JuheResult.class);
		if(juhe!=null && Const.ZERO.equals(juhe.getErrpr_code())){
			// 调用成功
			Object result = juhe.getResult();
			return JsonUtil.toObject(JsonUtil.toJson(result),SMSInfo.class);
		}

		return new SMSInfo(Code.TP_API_INVOKE_ERR,Code.TP_API_INVOKE_ERR_MSG+(juhe==null?"":juhe.getReason()));
	}



}
