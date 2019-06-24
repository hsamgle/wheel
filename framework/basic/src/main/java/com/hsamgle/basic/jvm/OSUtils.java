package com.hsamgle.basic.jvm;

import com.hsamgle.basic.entity.OSInfo;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;


/**
 *
 *  @feture   :	    TODO	获取操作系统相关的工具类
 *	@file_name:	    OSUtils.java
 * 	@packge:	    com.hsamgle.jvm
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:23
 *	@company:		江南皮革厂
 */
public final class OSUtils {



    /**
     *
     * @method:	TODO     获取操作系统相关信息
     * @time  :	2018/3/27 9:23
     * @author:	黄鹤老板
     * @param
     * @return:     OSInfo
     */
    public static OSInfo getOSInfo(){

        try {
            OperatingSystemMXBean osMBean = ManagementFactory.getOperatingSystemMXBean();
            if(osMBean!=null){
                OSInfo osInfo = new OSInfo();
                osInfo.setArch(osMBean.getArch());
                osInfo.setOsName(osMBean.getName());
                osInfo.setOsVersion(osMBean.getVersion());
                osInfo.setProcessors(osMBean.getAvailableProcessors());

                return osInfo;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
