package com.hsamgle.basic.config;

import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.util.StreamUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.HttpPutFormContentFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;



/**
 *
 *  @feture   :	    TODO		负责提取PUT参数
 *	@file_name:	    FormDataFilter.java
 * 	@packge:	    com.hsamgle.config
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:05
 *	@company:		江南皮革厂
 */
@Component
public class FormDataFilter extends HttpPutFormContentFilter{

	
	private final FormHttpMessageConverter formConverter = new AllEncompassingFormHttpMessageConverter();

	private final static String  TOKEN = "token";

	/**
	 * The default character set to use for reading form data.
	 */
	@Override
	public void setCharset(Charset charset) {
		this.formConverter.setCharset(charset);
	}

	@Override
	protected void doFilterInternal(final HttpServletRequest request, HttpServletResponse response,
			FilterChain filterChain) throws ServletException, IOException {
		
		ParameterRequestWrapper wrapper = new ParameterRequestWrapper(request);
		wrapper.addParameter(TOKEN,request.getHeader(TOKEN));

		if (("PUT".equals(request.getMethod()) || "PATCH".equals(request.getMethod()))) {
			Map<String, Object> params = read(request);
			wrapper.addAllParameters(params);
		}
		filterChain.doFilter(wrapper, response);
	}


	/**
	 *
	 *  @feture   :	    TODO		重新复写参数构造器
	 *	@file_name:	    FormDataFilter.java
	 * 	@packge:	    com.hsamgle.config
	 *	@author:	    黄鹤老板
	 *  @create_time:	2018/3/27 9:06
	 *	@company:		江南皮革厂
	 */
	public static class ParameterRequestWrapper extends HttpServletRequestWrapper {

	    private Map<String , String[]> params = new HashMap<>();
	 
	    private ParameterRequestWrapper(HttpServletRequest request) {
	        // 将request交给父类，以便于调用对应方法的时候，将其输出，其实父亲类的实现方式和第一种new的方式类似
	        super(request);
	        //将参数表，赋予给当前的Map以便于持有request中的参数
	        this.params.putAll(request.getParameterMap());
	    }

        //重写getParameter，代表参数从当前类中的map获取
	    @Override
	    public String getParameter(String name) {
	        String[]values = params.get(name);
	        if(values == null || values.length == 0) {
	            return null;
	        }
	        return values[0];
	    }

	    @Override
	    public String[] getParameterValues(String name) {//同上
	         return params.get(name);
	    }

		//增加多个参数
	   private void addAllParameters(Map<String , Object>otherParams) {
	        for(Map.Entry<String , Object>entry : otherParams.entrySet()) {
	            addParameter(entry.getKey() , entry.getValue());
	        }
	    }

		//增加参数
	    private void addParameter(String name , Object value) {
	        if(value != null) {
	            if(value instanceof String[]) {
	                params.put(name , (String[])value);
	            }else if(value instanceof String) {
	                params.put(name , new String[] {(String)value});
	            }else {
	                params.put(name , new String[] {String.valueOf(value)});
	            }
	        }
	    }
	}
	




    /**
     *
     * @method:	TODO        解释formdata 里面的参数
     * @time  :	2018/3/27 9:06
     * @author:	黄鹤老板
     * @param request
     * @return:     org.springframework.util.MultiValueMap<java.lang.String,java.lang.String>
     */
    private Map<String, Object> read(HttpServletRequest request) throws IOException, HttpMessageNotReadableException {

	    Charset charset = Charset.forName("UTF-8");
	    String body = StreamUtils.copyToString(request.getInputStream(), charset);
		body = URLDecoder.decode(body);
	    String[] pairs = StringUtils.tokenizeToStringArray(body, "&");
	    Map<String, Object> result = new HashMap<>(pairs.length);
	    for (String pair : pairs) {
		    int idx = pair.indexOf('=');
		    if (idx == -1) {
			    result.put(URLDecoder.decode(pair, charset.name()), null);
		    }else {
			    String name = URLDecoder.decode(pair.substring(0, idx), charset.name());
			    String value = URLDecoder.decode(pair.substring(idx + 1), charset.name());
			    result.put(name, value);
		    }
	    }
	    return result;
    }
}
