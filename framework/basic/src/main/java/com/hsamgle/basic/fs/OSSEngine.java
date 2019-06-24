package com.hsamgle.basic.fs;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

import java.io.InputStream;
import java.net.URI;


/**
 *
 *  @feture   :	    TODO		使用阿里云oss引擎作为云存储
 *	@file_name:	    OSSEngine.java
 * 	@packge:	    com.hsamgle.basic.fs
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/26 14:40
 *	@company:		江南皮革厂
 */
class OSSEngine implements BaseFileHelper {

	private  OSSClient client;

	private String bucket;

	private OSSEngine() {}


	private String baseRoot;

	/** 不允许外面直接初始化 */
	OSSEngine(OSSClient client,String bucket) {
		this.client = client;
		this.bucket = bucket;
		URI endpoint = client.getEndpoint();
		//TODO 所以临时改动https 微信小程序只能下载https资源
		baseRoot = "https://"+bucket+"."+endpoint.getHost()+"/";
	}

	/**
	 *
	 * @method:	TODO    上传文件
	 * @time  :	2018/5/26 16:27
	 * @author:	黄鹤老板
	 * @param fileName
	* @param stream
	 * @return:     java.lang.String
	 */
	@Override
	public String upload(String fileName,InputStream stream) throws Exception {

		// 校验捅是否存在
		if(!client.doesBucketExist(bucket)){
			throw new Exception("指定的 bucket 不存在");
		}
		client.putObject(bucket, fileName,stream);
		return baseRoot+fileName;
	}

	@Override
	public InputStream download(String fileName) throws Throwable {
		OSSObject ossObject = client.getObject(bucket, fileName);
		return ossObject.getObjectContent();
	}

	@Override
	public boolean delete(String fileName) throws Throwable {
		client.deleteObject(bucket,fileName);
		return true;
	}


}
