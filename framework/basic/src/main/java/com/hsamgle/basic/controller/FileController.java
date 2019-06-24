package com.hsamgle.basic.controller;

import com.hsamgle.basic.annotation.ACL;
import com.hsamgle.basic.constant.SecurityLevel;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.service.FileHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 *  @feture   :	    TODO		   图片上传工具类
 *	@file_name:	    FileController.java
 * 	@packge:	    com.hsamgle.basic.controller
 *	@author:	    黄鹤老板
 *  @create_time:	2018/6/2 11:54
 *	@company:		江南皮革厂
 */
@RestController
public class FileController{


	@Autowired
	private FileHelper fileHelper;

	/**
	 *
	 * @method:	TODO    上传文件
	 * @time  :	2018/6/2 11:52
	 * @author:	黄鹤老板
	 * @param file   可以同时上传一个或者多个文件
	 * @return:     com.hsamgle.basic.entity.ResponseEntity
	 */
	@ACL(level = SecurityLevel.PASS)
	@RequestMapping(value = "files",method = RequestMethod.POST)
	public ResponseEntity upload(MultipartFile[] file){
		return fileHelper.upload(file);
	}

}
