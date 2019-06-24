package com.hsamgle.basic.http;


import com.hsamgle.basic.entity.RequestEntity;
import com.hsamgle.basic.utils.JsonUtil;
import com.hsamgle.basic.utils.LogUtil;
import lombok.Cleanup;
import org.apache.http.HttpEntity;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpRequest;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.*;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

/**
 * 
 *  @类功能:	TODO  负责执行http 请求的工具类
 *	@文件名:	HttpExecutor.java
 * 	@所在包:	cn.com.dplus.d.http
 *	@开发者:	黄先国
 * 	@邮_件:     hsamgle@qq.com
 *  @时_间:		2016年7月19日下午3:38:17
 *	@公_司:		广州讯动网络科技有限公司
 */
public final class HttpExecutor {
	
    private static PoolingHttpClientConnectionManager cm;  
    private static final String UTF_8 = "UTF-8";
      
    static {
        if(cm == null){  
            cm = new PoolingHttpClientConnectionManager();
            //整个连接池最大连接数
            cm.setMaxTotal(50);
            //每路由最大连接数，默认值是2
            cm.setDefaultMaxPerRoute(5);
        }  
    }  
      
    /**
     * 
     * @方法功能：	TODO	通过连接池来获取httpclient 对象
     * @方法名称：	getHttpClient
     * @编写时间：	下午3:57:47
     * @开发者  ：	  黄先国
     * @方法参数：	@return
     * @返回值  :	CloseableHttpClient
     */
    private static CloseableHttpClient getHttpClient(URI uri) throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {

	    HttpClientBuilder builder = HttpClients.custom();

	    builder.setRetryHandler((exception, executionCount, context) -> {
			if(executionCount >= 3){
				return false;
			}

			if(exception instanceof UnknownHostException){
				// 未知服务器
				return false;
			}else if(exception instanceof SSLException){
				// SSL 异常
				return false;
			}

		    HttpClientContext clientContext = HttpClientContext.adapt(context);
		    HttpRequest request = clientContext.getRequest();
		    // 如果是幂等的请求的则再次重试，如果是非幂等的请求，则不再进行重试
		    return !(request instanceof HttpEntityEnclosingRequest);
	    });

	    if("http".equalsIgnoreCase(uri.getScheme())){
            return builder.setConnectionManager(cm).build();
	    }else{
		    SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, (chain, authType) -> true).build();
		    SSLConnectionSocketFactory ssf = new SSLConnectionSocketFactory(sslContext, (s, sslSession) -> true);
		    return builder.setSSLSocketFactory(ssf).build();
	    }
    }


    /**
     * 
     * @方法功能：	TODO	将参数信息转换为键值对
     * @方法名称：	covertParams2NVPS
     * @编写时间：	下午3:54:43
     * @开发者  ：	  黄先国
     * @方法参数：	@param params
     * @方法参数：	@return
     * @返回值  :	ArrayList<NameValuePair>
     */
    private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params){  
        ArrayList<NameValuePair> pairs = new ArrayList<>();
        for (Map.Entry<String, Object> param: params.entrySet()) {
            Object value = param.getValue();
            if(value!=null){
                if(value instanceof  String){
                    pairs.add(new BasicNameValuePair(param.getKey(), ""+value));
                }else{
                    pairs.add(new BasicNameValuePair(param.getKey(), JsonUtil.toJson(value)));
                }
            }
        }
        return pairs;
    }



  /**
   * 
   * @方法功能：	TODO	开始执行HTTP 请求
   * @方法名称：	getResult
   * @编写时间：	下午3:56:48
 * @throws IOException 
 * @throws ClientProtocolException 
   * @开发者  ：	  黄先国
   * @方法参数：	@param request
   * @方法参数：	@return
   * @返回值  :	String
   */
	private static String getResult(HttpRequestBase request) throws Exception{
        int statusCode;
        try {

	        URI uri = request.getURI();
            CloseableHttpClient httpClient = getHttpClient(uri);

	        LogUtil.console("method -> "+request.getMethod()+" | url -> "+ uri);

            CloseableHttpResponse  response = httpClient.execute(request);
            statusCode = response.getStatusLine().getStatusCode();
            if(statusCode==200){
                HttpEntity entity = response.getEntity();
                if(entity!=null){
                    String result = EntityUtils.toString(entity);
                    response.close();
                    return result;
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return  null;
    }

	/**
	 *
	 * @method:	TODO    一般用来下载资源文件
	 * @time  :	2018/11/6 11:01
	 * @author:	黄鹤老板
	 * @param url
	 * @return:     byte[]
	 */
    public static byte[] getByte(String url){
        HttpGet httpGet = new HttpGet(url);
        return getResultByte(httpGet);
    }


	private static byte[] getResultByte(HttpRequestBase request) {
		try {
			URI uri = request.getURI();
			CloseableHttpClient httpClient = getHttpClient(uri);
			@Cleanup CloseableHttpResponse response = httpClient.execute(request);
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				@Cleanup ByteArrayOutputStream outstream = new ByteArrayOutputStream();
				entity.writeTo(outstream);
				return outstream.toByteArray();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 *
	 * @method:	TODO    执行http请求
	 * @time  :	2018/11/6 10:59
	 * @author:	黄鹤老板
	 * @param entity
	 * @return:     java.lang.String
	 */
    public static String execute(RequestEntity entity) throws Exception {

	    String url = entity.getUrl();

	    Map<String, Object> path = entity.getPath();
	    if (path!=null){
		    for (Map.Entry<String, Object> entry : path.entrySet()) {
			    url =  url.replace("{"+entry.getKey()+"}", String.valueOf(entry.getValue()));
		    }
	    }

	    URIBuilder builder = new URIBuilder();
	    Map<String, Object> query = entity.getQuery();
	    if(query!=null){
		    ArrayList<NameValuePair> pairs = covertParams2NVPS(query);
		    builder.addParameters(pairs);
	    }

	    builder.setPath(url);

	    URI uri = builder.build();

	    HttpRequestBase request;
	    RequestMethod method = entity.getMethod();
	    switch (method){
			case GET:
				request = new HttpGet(uri);
				break;
			case PUT:
				request = new HttpPut(uri);
				break;
			case POST:
				request = new HttpPost(uri);
				break;
			case DELETE:
				request = new HttpDelete(uri);
				break;
			default:
				request = new HttpGet(uri);
				break;
		}

	    Map<String, Object> headers = entity.getHeaders();
	    if(headers!=null){
			for (Map.Entry<String, Object> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(),String.valueOf(entry.getValue()));
			}
		}

	    Map<String, Object> formData = entity.getFormData();
	    if((POST == method || PUT == method) && formData!=null){
		    List<NameValuePair> pairs = covertParams2NVPS(formData);
		    ((HttpEntityEnclosingRequestBase)request).setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
	    }

	    return getResult(request);
    }

}