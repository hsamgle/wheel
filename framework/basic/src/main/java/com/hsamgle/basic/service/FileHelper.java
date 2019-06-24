package com.hsamgle.basic.service;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.fs.FileEngine;
import lombok.Cleanup;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


@Service
public  class FileHelper {

	/**
	 *
	 * @method:	TODO    执行文件上传
	 * @time  :	2018/6/2 11:57
	 * @author:	黄鹤老板
	 * @param files
	 * @return:     com.hsamgle.basic.entity.ResponseEntity
	 */
	public ResponseEntity upload(MultipartFile[] files){

		try {
			List<String> urls = new ArrayList<>();
			for (MultipartFile file : files) {

				@Cleanup InputStream inputStream = file.getInputStream();
				String originalFilename = file.getOriginalFilename();
				String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
				String upload = FileEngine.upload(inputStream, fileType);
				urls.add(upload);
			}
			return ResponseEntity.build(urls);
		}catch (Exception e){
		    e.printStackTrace();
		    if(e instanceof IllegalStateException){
		    	return ResponseEntity.build(Code.SERVER_403,Code.SERVER_403_MSG);
		    }
		    return ResponseEntity.build(Code.SERVER_ERR,Code.SERVER_ERR_MSG);
		}
	}

	public String upload(File file){
		String url = "";
		try {
			@Cleanup FileInputStream inputStream = new FileInputStream(file);
			String originalFilename = file.getName();
			String fileType = originalFilename.substring(originalFilename.lastIndexOf("."));
			url = FileEngine.upload(inputStream, fileType);
		}catch (Exception e){
			e.printStackTrace();
		}
		return url;
	}

}
