package com.hsamgle.basic.controller;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.utils.LogUtil;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 *
 *  @feture   :	    TODO		日志控制器
 *	@file_name:	    LogUtilsController.java
 * 	@packge:	    com.hsamgle.basic.controller
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:10
 *	@company:		江南皮革厂
 */
@RestController
public class LogUtilsController {

    /**
     *
     * @method:	TODO    切换输出日志级别,
     *                    没有指定类名的时候，默认控制控制台输出
     * @time  :	2018/3/27 9:10
     * @author:	黄鹤老板
     * @param logger        logger name
     * @param level         输出级别
     * @return:     ResponseEntity
     */
	@RequestMapping(value = "/loggers",method = RequestMethod.PUT)
	public ResponseEntity switchLevel( String logger,String level){

		// 切换级别
        try {
        	if(!StringUtils.isEmpty(level)){
          		return ResponseEntity.build(LogUtil.changeLevel(logger, level.toUpperCase()));
            }
			LogUtil.CONSOLE_VIABLE = !LogUtil.CONSOLE_VIABLE;
			return  ResponseEntity.build(LogUtil.CONSOLE_VIABLE);
        }catch (Exception e){
            e.printStackTrace();
		    return  ResponseEntity.build(Code.SERVER_ERR,e.getMessage());
        }
	}


    /**
     *
     * @method:	TODO    获取系统中所有的日志对象
     * @time  :	2018/3/27 9:11
     * @author:	黄鹤老板
     * @param
     * @return:     ResponseEntity
     */
	@RequestMapping(value = "loggers",method = RequestMethod.GET)
	public ResponseEntity getLoggers(){
	    return  ResponseEntity.build(LogUtil.getLoggers());
    }

}
