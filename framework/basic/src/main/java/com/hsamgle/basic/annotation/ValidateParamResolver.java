package com.hsamgle.basic.annotation;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.exception.ParamsErrorException;
import com.hsamgle.basic.exception.ParamsMissException;
import com.hsamgle.basic.utils.DateTimeUtils;
import com.hsamgle.basic.utils.FieldUtils;
import com.hsamgle.basic.utils.JsonUtil;
import com.hsamgle.basic.utils.PatternUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.NumberUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

/**
 *
 *  @feture   :	    TODO		定义实现了自定的参数校验器
 *	@file_name:	    ValidateParamResolver.java
 * 	@packge:	    com.hsamgle.basic.annotation
 *	@author:	    黄鹤老板
 *  @create_time:	2018/5/21 9:44
 *	@company:		江南皮革厂
 */
public class ValidateParamResolver implements HandlerMethodArgumentResolver {


	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return true;
	}

	private final static String RQ_BODY = "RQBody";

	/**
	 *
	 * @method:	TODO        执行参数校验
	 * @time  :	2018/5/21 9:45
	 * @author:	黄鹤老板
	 * @param methodParameter
	* @param modelAndViewContainer
	* @param nativeWebRequest
	* @param webDataBinderFactory
	 * @return:     java.lang.Object
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Object resolveArgument(MethodParameter methodParameter,
	                              ModelAndViewContainer modelAndViewContainer,
	                              NativeWebRequest nativeWebRequest,
	                              WebDataBinderFactory webDataBinderFactory) throws Exception {

		HttpServletRequest request = (HttpServletRequest) nativeWebRequest.getNativeRequest();

		String method  = request.getMethod().toLowerCase();

		// 获取参数名称
		String parameter = methodParameter.getParameterName();

		// 获取参数类型
		Class<?> parameterType = methodParameter.getParameterType();

		// 获取参数值
		Object value = request.getParameter(parameter);

		if( "createTime".equals(parameter) || "updateTime".equals(parameter)){
			return null;
		}

		if( StringUtils.isEmpty(value) || "".equals(value.toString().replaceAll("\\s*", ""))){
			value = null;
		}

		// 获取参数注解
		ParamsValid validation = methodParameter.getParameterAnnotation(ParamsValid.class);
		if(validation!=null){
			// 验证参数合法性，并且返回参数真实类型
			if(value==null){
				String alias = validation.alias();
				if(!StringUtils.isEmpty(alias)){
					value = request.getParameter(alias);
				}
			}
			return this.validate(request,validation, parameter, parameterType, value);
		}else{
			// 不加注解的话直接返回
			if(!StringUtils.isEmpty(value)){
				value = casting(null,parameterType,value,null);
			}
			return value;
		}
	}


	/**
	 *
	 * @方法功能：	TODO	执行验证
	 * @方法名称：	validate
	 * @编写时间：	2016年12月2日下午2:17:22
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param validation		注解接口
	 * @方法参数：	@param parameter		参数
	 * @方法参数：	@param parameterType	参数类型
	 * @方法参数：	@param value			参数的值
	 * @方法参数：	@return
	 * @方法参数：	@throws IllegalArgumentException
	 * @返回值  :	Object
	 */
	private Object validate(HttpServletRequest request,ParamsValid validation, String parameter,
	                        Class<?> parameterType, Object value) throws Exception {

		// 现行判断
		validate(validation, parameter, parameterType, value);

		/** 验证实体对象参数中的注解  */
		if(validation.isEntity()){

			// 需要校验的参数
			final List<String> needArgs = Arrays.asList(validation.needArgs());

			// 需要排除的参数
			final List<String> exclude = Arrays.asList(validation.exclude());
			Class<?> obj = Class.forName(parameterType.getName());
			List<Field> allFields = FieldUtils.getAllFields(obj);

			value = obj.newInstance();
			for (Field field : allFields) {
				String fieldName = field.getName();

                if( "createTime".equals(fieldName) || "updateTime".equals(fieldName)){
                    continue;
                }

				// 修改访问权限
				field.setAccessible(true);
				if (!"serialVersionUID".equals(fieldName)) {

					// 优先执行参数级别的校验
					ParamsValid paramsValid = field.getAnnotation(ParamsValid.class);
					Object param = request.getParameter(fieldName);

					if(paramsValid!=null){

						if(paramsValid.filters()){
							continue;
						}
						if(param==null){
							// 判断是否映射了别名
							String alias = paramsValid.alias();
							String def = paramsValid.def();
							if(!StringUtils.isEmpty(alias)){
								param = request.getParameter(alias);
							}else if ( !StringUtils.isEmpty(def) ){
								param = def;
							}
						}
					}

					Class<?> type = field.getType();

					if(needArgs.contains(fieldName)){
						validate(paramsValid, fieldName, type, param);
					}else if(exclude.contains(fieldName)){
						// 排除的参数不做返回处理
						continue;
					}
					if(!StringUtils.isEmpty(param)){
						param = casting(field,type,param,validation);
						field.set(value,param);
					}
				}
			}
		}else{
			if(!StringUtils.isEmpty(value)){
				value = casting(null,parameterType,value,validation);
			}
		}

		return value;
	}




	/**
	 *
	 * @方法功能：	TODO    数据转型
	 * @编写时间：	2018/3/26 16:33
	 * @author：	黄先国 | hsamgle@qq.com
	 * * @param type
	* @param value
	 * @return     java.lang.Object
	 */
	private Object casting(Field field,Class<?> parameterType,Object value,ParamsValid valid){
		String typeName = parameterType.getSimpleName();
		if("String".equals(typeName)){
			value = String.valueOf(value);
		}else if("Integer".equals(typeName)){
			value = Integer.valueOf(value.toString());
		}else if ("Float".equals(typeName)) {
			value = Float.valueOf(value.toString());
		}else if("Long".equals(typeName)){
			value = Long.valueOf(value.toString());
		}else if("Double".equals(typeName)){
			value = Double.valueOf(value.toString());
		}else if("Boolean".equals(typeName)){
			value = Boolean.valueOf(value.toString());
		}else if(typeName.contains("List") || typeName.contains("Set") || typeName.contains("Map")){
            Type type = field.getGenericType();
            if(type!=null){
                return JsonUtil.toObject(value.toString(),type);
            }
		}else if("String[]".equals(typeName)){
            String split = valid!=null&&!StringUtils.isEmpty(valid.split())?valid.split():",";
            return value.toString().split(split);
        } else if("Date".equals(typeName) || "Timestamp".equals(typeName)){
			try {
				String v = value.toString();
				String format = "";
				if(PatternUtils.isDate(v)){
					format = "yyyy-MM-dd";
				}else if(PatternUtils.isDateTime(v)){
					format = "yyyy-MM-dd HH:mm:ss";
				}
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				value = sdf.parse(value.toString());
				if("Timestamp".equals(typeName)){
					return new Timestamp(((Date)value).getTime());
				}
			}catch (Exception e){
				value = null;
			}
		}
		return value;
	}


	/**
	 *
	 * @方法功能：	TODO	执行校验
	 * @方法名称：	validata
	 * @编写时间：	2017年1月13日下午4:02:02
	 * @开发者  ：	  黄先国
	 * @方法参数：	@param validation
	 * @方法参数：	@param parameter
	 * @方法参数：	@param parameterType
	 * @方法参数：	@param value
	 * @返回值  :	void
	 */
	private void validate(ParamsValid validation, String parameter,Class<?> parameterType, Object value) throws ParamsErrorException,ParamsMissException {

		if(validation==null){
			if(StringUtils.isEmpty(value)){
				throw new ParamsMissException(parameter);
			}
			return;
		}

		/**
		 * 参数默认值
		 */
		if(!StringUtils.isEmpty(validation.def()) && value == null){
			value = validation.def();
		}


		/**
		 * 验证参数是否可为空 如果不为空，则必须传递该参数，并且参数的值不可为NUll
		 */
		if (validation.notNull() && StringUtils.isEmpty(value) && !validation.isEntity()) {
			// 空值的时候提示信息
			String msg = validation.nullMsg();
			if(StringUtils.isEmpty(msg)){
				throw new ParamsMissException(parameter);
			}else{
				throw new ParamsMissException(parameter,msg);
			}
		}

		/**
		 * 判断是否为整型
		 */
		if (!StringUtils.isEmpty(value) && validation.isInt()) {
			try {
				Integer.parseInt(value.toString());
			} catch (Exception e) {
				throw new ParamsErrorException("参数要求整型参数",parameter, Code.PARAM_TYPE_ERR);
			}
		}


		/**
		 *  判断是否为浮点型
		 */
		if (!StringUtils.isEmpty(value) && validation.isFloat()) {
			try {
				Float.parseFloat(value.toString());
			} catch (Exception e) {
				throw new ParamsErrorException("参数要求浮点型参数",parameter,Code.PARAM_TYPE_ERR);
			}
		}

		/**
		 * 验证最小值
		 */
		if (!StringUtils.isEmpty(value) && validation.min() != -1 ) {
			Number number;
			try {
				number = NumberUtils.parseNumber(value.toString(), Number.class);
			} catch (Exception e) {
				throw new ParamsErrorException("参数要求为数字类型",parameter,Code.PARAM_TYPE_ERR);
			}
			double v = number.doubleValue();
			if(v < validation.min()){
				throw new ParamsErrorException("参数值小于   "+validation.min(),parameter,Code.PARAM_VAL_ERR);
			}
		}

		/**
		 * 验证最大值
		 */
		if (!StringUtils.isEmpty(value) && validation.max() != -1) {
			Number number;
			try {
				number = NumberUtils.parseNumber(value.toString(), Number.class);
			} catch (Exception e) {
				throw new ParamsErrorException("参数要求为数字类型",parameter,Code.PARAM_TYPE_ERR);
			}
			double v = number.doubleValue();
			if(v > validation.max()){
				throw new ParamsErrorException("参数值大于   "+validation.max(),parameter,Code.PARAM_VAL_ERR);
			}
		}

		/**
		 * 验证参数的最小长度
		 */
		if (!StringUtils.isEmpty(value) && validation.minLen() != -1 && value.toString().length() < validation.minLen()) {
			throw new ParamsErrorException("参数内容长度小于     "+validation.minLen(),parameter,Code.PARAM_LEN_ERR);
		}

		/**
		 * 验证参数的最大长度
		 */
		if (!StringUtils.isEmpty(value) && validation.maxLen() != -1  && value.toString().length() > validation.maxLen()) {
			throw new ParamsErrorException("参数内容长度大于     "+validation.maxLen(),parameter, Code.PARAM_LEN_ERR);
		}

		/**
		 * 通过正则表达式来校验
		 */
		if(!StringUtils.isEmpty(value) && !StringUtils.isEmpty(validation.reg())){
			Pattern pattern = Pattern.compile(validation.reg());
			if(!pattern.matcher(value+"").find()){
				throw new ParamsErrorException("参数格式异常    "+validation.reg(), parameter, Code.PARAM_FORMAT_ERR);
			}
		}

		/**
		 * 判断是不是时间类型
		 */
		if(!StringUtils.isEmpty(value) && validation.isTime()){
			if(!PatternUtils.isDate(value.toString()) && !PatternUtils.isDateTime(value.toString())){
				throw new ParamsErrorException("时间格式异常  (yyyy-MM-dd) 或者 (yyyy-MM-dd HH:mm:ss) ", parameter, Code.PARAM_FORMAT_ERR);
			}
		}

	}


}
