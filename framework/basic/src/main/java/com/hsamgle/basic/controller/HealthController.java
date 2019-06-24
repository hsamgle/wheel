package com.hsamgle.basic.controller;

import com.hsamgle.basic.constant.Code;
import com.hsamgle.basic.entity.ResponseEntity;
import com.hsamgle.basic.jvm.MemoryUtils;
import com.hsamgle.basic.jvm.OSUtils;
import com.hsamgle.basic.jvm.ThreadUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.TreeMap;


/**
 *
 *  @feture   :	    TODO		服务健康检查接口
 *	@file_name:	    HealthController.java
 * 	@packge:	    com.hsamgle.basic.controller
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:09
 *	@company:		江南皮革厂
 */
@RestController
public class HealthController {


    /**
     *
     * @方法功能：	TODO    健康检查
     * @编写时间：	2018/9/26 10:42
     * @author：	黄先国 | hsamgle@qq.com
     * * @param
     * @return     cn.com.dplus.project.entity.ResponseEntity
     */
    @RequestMapping(value="healthz",method=RequestMethod.GET)
    public ResponseEntity healthZ(){
        return ResponseEntity.build("ok");
    }


    private static final TreeMap<String,String> METRIC_TIPS = new TreeMap<>();

    static {
        METRIC_TIPS.put("mem","查看内存统计信息");
        METRIC_TIPS.put("os","查看系统相关信息");
        METRIC_TIPS.put("thread","查看线程统计信息");
    }

    /**
     *
     * @方法功能：	TODO    获取系统指标度量值
     * @编写时间：	2018/9/26 10:42
     * @author：	黄先国 | hsamgle@qq.com
     * * @param type
     * @return     cn.com.dplus.project.entity.ResponseEntity
     */
    @RequestMapping(value = "metric",method = RequestMethod.GET)
    public ResponseEntity metric(String type){

        if( StringUtils.isEmpty(type)){
            return  ResponseEntity.build(METRIC_TIPS);
        }
        switch (type){
            case "mem":
                return  ResponseEntity.build(MemoryUtils.getMemoryInfo());
            case "os":
                return  ResponseEntity.build(OSUtils.getOSInfo());
            case "thread":
                return  ResponseEntity.build(ThreadUtils.getThreadInfo());
            default:
                return ResponseEntity.build(Code.PARAMS_ERR,"无法识别的指令");
        }
    }


}
