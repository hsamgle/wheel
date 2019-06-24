package com.hsamgle.basic.controller;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.ServletWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


/**
 *
 *  @feture   :	    TODO		异常的http 请求回应
 *	@file_name:	    ErrHttpResponseController.java
 * 	@packge:	    com.hsamgle.basic.controller
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:08
 *	@company:		江南皮革厂
 */
@RestController
public class ErrHttpResponseController  {

	@Autowired
	private ErrorAttributes errorAttributes;

    /**
     *
     * @method:	TODO    返回404 错误信息
     * @time  :	2018/3/27 9:09
     * @author:	黄鹤老板
     * @param request
     * @return:     ResponseEntity
     */
	@RequestMapping(value = "404")
	public ResponseEntity error404(HttpServletRequest request) {
		Map<String,Object> errorAttributes = getErrorAttributes(request, false);
		String path = (String) errorAttributes.get("path");
		return ResponseEntity.build(Code.HTTP_404,Code.HTTP_404_MSG + "  --> "+path);
	}

    /**
     *
     * @method:	TODO    请求方法异常
     * @time  :	2018/3/27 9:09
     * @author:	黄鹤老板
     * @param request
     * @return:     ResponseEntity
     */
	@RequestMapping(value = "405")
    public ResponseEntity error405(HttpServletRequest request){
		Map<String,Object> errorAttributes = getErrorAttributes(request, false);
		String path = (String) errorAttributes.get("path");
		return  ResponseEntity.build(Code.HTTP_404,Code.HTTP_404_MSG + " -> "+path);
    }



    /**
     *
     * @method:	TODO    返回505 服务器错误信息
     * @time  :	2018/3/27 9:09
     * @author:	黄鹤老板
     * @param request
     * @return:     ResponseEntity
     */
	@RequestMapping(value = "500")
	public ResponseEntity error500(HttpServletRequest request) {

		return ResponseEntity.build(Code.SERVER_ERR,Code.SERVER_ERR_MSG);
	}



	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		ServletWebRequest attributes = new ServletWebRequest(request);
		return errorAttributes.getErrorAttributes(attributes, includeStackTrace);
	}
}
