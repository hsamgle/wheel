package com.hsamgle.basic.exception;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.utils.LogUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @类功能: TODO 全局的异常拦截
 * @文件名: GlobalExceptionHandler.java
 * @所在包: com.com.dplus.project.annotation
 * @开发者: 黄先国
 * @邮_件: hsamgle@qq.com
 * @时_间: 2016年12月2日下午2:15:27
 * @公_司: 广州讯动网络科技有限公司
 */
@ControllerAdvice
public class GlobalExceptionHandler {
	
	
	@ExceptionHandler(value={Exception.class,Throwable.class})
	@ResponseBody
	public ResponseEntity defaultErrorHandler(HttpServletRequest request,
			HttpServletResponse response, Exception e,Throwable te)
			throws Exception {

		if (e instanceof ParamsMissException) {
			// 参数缺失异常
			ParamsMissException exception = (ParamsMissException) e;
			ResponseEntity entity = ResponseEntity.build(Code.PARAMS_MISS, exception.getMsg(),exception.getParam());
			
			LogUtil.error("参数缺失异常  "+entity.toJson());
			return entity;
		} else if(e instanceof ParamsErrorException){
			// 参数错误异常
			ParamsErrorException exception = (ParamsErrorException) e;
			
			ResponseEntity entity = ResponseEntity.build(
					exception.getCode(),
					exception.getMsg(),exception.getParam());

            LogUtil.error("参数错误异常  "+entity.toJson());
			return entity;
		}else if(e instanceof NumberFormatException){
            String message = ""+e.getMessage();
            ResponseEntity entity = ResponseEntity.build(Code.PARAM_TYPE_ERR, message.replace("For input string: ","不匹配的数据类型: "));
            LogUtil.error("\n请求:  "+request.getRequestURI()+"\n参数错误异常  "+entity.toJson());
            return entity;
		}else{
			// 参数错误异常
            LogUtil.error(te);
			ResponseEntity entity = ResponseEntity.build(Code.SERVER_ERR,Code.SERVER_ERR_MSG);
			LogUtil.error("其他异常  "+entity.toJson());
			return entity;
		}
	}
	
}