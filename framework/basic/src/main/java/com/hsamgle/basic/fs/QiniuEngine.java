package com.hsamgle.basic.fs;

import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;

import java.io.InputStream;

/**
 *
 *  @feture   :	    TODO	    使用七牛云作为云存储
 *	@file_name:	    QiniuEngine.java
 * 	@packge:	    com.hsamgle.basic.fs
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/26 14:40
 *	@company:		江南皮革厂
 */
class QiniuEngine implements BaseFileHelper {

	private UploadManager uploadManager;

	private String token;

	private QiniuEngine() {}

	private String baseRoot;

	QiniuEngine(UploadManager uploadManager, String token) {
		this.uploadManager = uploadManager;
		this.token = token;
		//TODO 暂时写死
		baseRoot = "http://pl693ne6c.bkt.clouddn.com/";
	}

	/**
	 *
	 * @方法功能：	TODO    上传文件到七牛云
	 * @编写时间：	2018/5/26 16:30
	 * @author：	黄先国 | hsamgle@qq.com
	 * * @param fileName
	* @param stream
	 * @return     java.lang.String
	 */
	@Override
	public String upload(String fileName, InputStream stream) throws Exception{

		byte[] bytes = new byte[stream.available()];
		stream.read(bytes);
        Response response = uploadManager.put(bytes, fileName, token);
        if(response.statusCode == 200){
            return baseRoot+fileName;
        }
        return null;
	}

	/**
	 *
	 * @method:	TODO    暂不实现
	 * @time  :	2018/5/26 16:30
	 * @author:	黄鹤老板
	 * @param fileName
	 * @return:     boolean
	 */
	@Override
	public boolean delete(String fileName)throws Throwable {

		return false;
	}

	/**
	 *
	 * @method:	TODO    暂不实现
	 * @time  :	2018/5/26 16:30
	 * @author:	黄鹤老板
	 * @param fileName
	 * @return:     boolean
	 */
	@Override
	public InputStream download(String fileName) throws Throwable{
		return null;
	}
}
