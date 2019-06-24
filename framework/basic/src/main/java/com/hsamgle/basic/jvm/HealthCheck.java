package com.hsamgle.basic.jvm;

import com.hsamgle.basic.entity.JVMInfo;


/**
 *
 *  @feture   :	    TODO		JVM 健康检查
 *	@file_name:	    HealthCheck.java
 * 	@packge:	    com.hsamgle.jvm
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:20
 *	@company:		江南皮革厂
 */
public class HealthCheck {


    /**
     *
     * @method:	TODO    JVM 健康检查
     * @time  :	2018/3/27 9:20
     * @author:	黄鹤老板
     * @param
     * @return:     JVMInfo
     */
    public static synchronized JVMInfo healthCheck(){

        try {

            JVMInfo jvm = new JVMInfo();
            jvm.setHeapMemory(MemoryUtils.getJvmHeapMemoryInfo());
            jvm.setMemoryInfo(MemoryUtils.getJvmMemoryInfo());
            jvm.setNonHeapMemory(MemoryUtils.getJvmNonHeapMemoryInfo());
            jvm.setOsInfo(OSUtils.getOSInfo());
            jvm.setThreadInfo(ThreadUtils.getThreadInfo());
            return jvm;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}
