package com.hsamgle.basic.fs;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;
import com.qiniu.common.Zone;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.io.InputStream;

/**
 *
 *  @feture   :	    TODO		文件引擎初始化
 *	@file_name:	    FileEngine.java
 * 	@packge:	    com.hsamgle.basic.fs
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/26 13:13
 *	@company:		江南皮革厂
 */
@Component
public class FileEngine{

	private static final String OSS = "oss";

	private static final String QINIU = "qiniu";


	/** 文件引擎 */
	private static String engine;
	@Value("${file.engine:oss}")
	public void setEngine(String e) {
		engine = e;
	}

	/** 文件存储节点 */
	private static String endpoint;
	@Value("${file.endpoint:null}")
	public void setEndpoint(String e) {
		endpoint = e;
	}

	private static String accessKeyId;
	@Value("${file.accesskey.id}")
	public void setAccessKeyId(String id) {
		accessKeyId = id;
	}

	private static String accessKeySecret;
	@Value("${file.accesskey.secret}")
	public void setAccessKeySecret(String secret) {
		accessKeySecret = secret;
	}

	private static String bucketName;
	@Value("${file.bucket}")
	public  void setBucketName(String bucket) {
		bucketName = bucket;
	}

	private static BaseFileHelper fileHelper;

	/**
	 *
	 * @method:	TODO    进行文件引擎初始化
	 * @time  :	2018/5/26 13:20
	 * @author:	黄鹤老板
	 * @param
	 * @return:     boolean
	 */
	public static boolean init() throws Exception{


		if(OSS.equalsIgnoreCase(engine)){
			// 选择的是oss引擎
			ClientConfiguration config = new ClientConfiguration();

			// 设置OSSClient允许打开的最大HTTP连接数，默认1024。
			config.setMaxConnections(200);

			// 设置Socket层传输数据的超时时间（单位：毫秒），默认为50000毫秒。
			config.setSocketTimeout(10000);

			//设置建立连接的超时时间（单位：毫秒），默认为50000毫秒。
			config.setConnectionTimeout(10000);

			//设置从连接池中获取连接的超时时间（单位：毫秒）,默认不超时。
			config.setConnectionRequestTimeout(1000);

			//如果空闲时间超过此参数的设定值，则关闭连接（单位：毫秒），默认为60000毫秒 。
			config.setIdleConnectionTime(10000);

			// 设置失败请求重试次数，默认3次。
			config.setMaxErrorRetry(5);

			OSSClient ossClient = new OSSClient(endpoint,accessKeyId,accessKeySecret,config);
			fileHelper = new OSSEngine(ossClient,bucketName);
		}else if(QINIU.equalsIgnoreCase(engine)){
			// 选择的是七牛云
			Auth auth = Auth.create(accessKeyId, accessKeySecret);
			Configuration configuration = new Configuration(Zone.autoZone());
			UploadManager uploadManager = new UploadManager(configuration);
			String token = auth.uploadToken(bucketName);
			fileHelper = new QiniuEngine(uploadManager,token);
		}else{
			throw new Exception("无法识别的文件引擎");
		}
		return true;
	}


	/**
	 *
	 * @method:	TODO    执行文件上传
	 * @time  :	2018/5/26 14:41
	 * @author:	黄鹤老板
	 * @param fileName
	* @param stream
	 * @return:     java.lang.String
	 */
	public static String upload(String fileName, InputStream stream) throws Exception {

		Assert.isTrue(fileHelper!=null,"文件引擎尚未初始化");

		if(StringUtils.isNullOrEmpty(fileName)){
			throw  new Exception("不要将文件名显式置空");
		}
		return fileHelper.upload(fileName,stream);
	}

	/**
	 *
	 * @method:	TODO    执行文件上传
	 * @time  :	2018/5/26 14:41
	 * @author:	黄鹤老板
	* @param stream
	 * @return:     java.lang.String
	 */
	public static String upload(InputStream stream,String fileType) throws Exception {

		Assert.isTrue(fileHelper!=null,"文件引擎尚未初始化");

		String fileName = System.nanoTime()+(fileType.contains(".")?"":".")+fileType;
		return fileHelper.upload(fileName,stream);
	}

	/**
	 *
	 * @method:	TODO    执行文件下载
	 * @time  :	2018/5/26 14:42
	 * @author:	黄鹤老板
	 * @param id
	 * @return:     java.io.InputStream
	 */
	public static InputStream download(String id) throws Throwable {

		Assert.isTrue(fileHelper!=null,"文件引擎尚未初始化");

		return fileHelper.download(id);
	}

	/**
	 *
	 * @method:	TODO    删除文件
	 * @time  :	2018/5/26 14:42
	 * @author:	黄鹤老板
	 * @param id
	 * @return:     boolean
	 */
	public static boolean delete(String id) throws Throwable {

		Assert.isTrue(fileHelper!=null,"文件引擎尚未初始化");

		return fileHelper.delete(id);
	}
}
