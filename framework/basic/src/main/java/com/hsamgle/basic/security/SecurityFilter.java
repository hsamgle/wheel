package com.hsamgle.basic.security;

import com.hsamgle.basic.annotation.ACL;
import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.constant.RequestMethodEnum;
import com.hsamgle.basic.constant.SecurityLevel;
import com.hsamgle.basic.entity.License;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.entity.TokenInfo;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Set;


/**
 *
 *  @feture   :	    TODO		负责请求的安全校验
 *	@file_name:	    SecurityFilter.java
 * 	@packge:	    com.hsamgle.basic.security
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:32
 *	@company:		江南皮革厂
 */
public final class SecurityFilter {


    /**
     *
     * @method:	TODO        执行安全校验
     * @time  :	2018/3/27 9:32
     * @author:	黄鹤老板
     * @param request
     * @return:     com.hsamgle.basic.entity.ResponseEntity
     */
    public static ResponseEntity securityCheck(HttpServletRequest request,Method method){

        try {

	        // token 需要放在头部进行传输
	        String token = request.getHeader("token");

	        ACL annotation = method.getAnnotation(ACL.class);

	        // 正在上线后，这里的默认拦截级别需要更改
	        SecurityLevel security = annotation==null?SecurityLevel.PERMISSION:annotation.level();

			if(security.level > SecurityLevel.PASS.level){

				TokenInfo tokenInfo = TokenManager.getTokenInfo(token);

				if(tokenInfo!=null){
				    if(tokenInfo.isSuperAdmin()){
				        return null;
                    }else if(security.level ==SecurityLevel.WECHAT.level && tokenInfo.isWechatUser()){
                        // 是微信用户且调用的是微信端业务请求
                        return null;
                    }
				}

				Assert.isTrue(TokenManager.checkToken(token),"TK");

				if(security.level > SecurityLevel.NORMAL.level){

					// 校验签名
					Assert.isTrue(checkSign(),"SIGN");

					// 校验权限
					if(security.level > SecurityLevel.SIGNATURE.level){

						Assert.isTrue(checkPermission(tokenInfo,request,method),"PROHIBITED");

					}
				}
			}

			return null;

        }catch (Exception e){
            if(e instanceof  IllegalArgumentException){
                switch ( e.getMessage() ){
                    case "TK":
                        return  ResponseEntity.build(Code.TOKEN_INVALID,Code.TOKEN_INVALID_MSG);
                    case "SIGN":
                        return  ResponseEntity.build(Code.SIGN_ERR,Code.SIGN_ERR_MSG);
                    case "PROHIBITED":
                        return  ResponseEntity.build(Code.PROHIBITED,Code.PROHIBITED_MSG);
                    default:
                        break;
                }
            }
        }
        return null;
    }





    /**
     *
     * @方法功能：	TODO    校验参数签名合法性
     * @编写时间：	2018/3/27 9:33
     * @author：	黄先国 | hsamgle@qq.com
     * * @param
     * @return     boolean
     */
    private static boolean checkSign(){


        return true;
    }



    /**
     *
     * @method:	TODO    校验权限合法性
     * @time  :	2018/3/27 9:33
     * @author:	黄鹤老板
     * @param
     * @return:     boolean
     */
    private static boolean checkPermission(TokenInfo tokenInfo,HttpServletRequest request,Method method){

        try {

        	if(tokenInfo!=null){
		        String reqMethod = request.getMethod();

		        String[] urls = method.getAnnotation(RequestMapping.class).value();
		        String url = urls[0];

		        Set<License> licenses = tokenInfo.getLicenses();
				if(licenses!=null && !licenses.isEmpty() && licenses.contains(new License(url, RequestMethodEnum.getKeyByName(reqMethod)))){
					return true;
				}
	        }
        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }


}
