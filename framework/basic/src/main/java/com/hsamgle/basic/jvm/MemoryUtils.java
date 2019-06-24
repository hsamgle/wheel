package com.hsamgle.basic.jvm;

import com.hsamgle.basic.entity.HeapMemory;
import com.hsamgle.basic.entity.MemoryInfo;
import com.hsamgle.basic.entity.NonHeapMemory;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.HashMap;
import java.util.Map;


/**
 *
 *  @feture   :	    TODO		获取的jvm内存信息相关工具类
 *	@file_name:	    MemoryUtils.java
 * 	@packge:	    com.hsamgle.jvm
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:21
 *	@company:		江南皮革厂
 */
public final class MemoryUtils {

    /**
     *
     * @method:	TODO    获取堆内存信息
     * @time  :	2018/3/27 9:22
     * @author:	黄鹤老板
     * @param
     * @return:     HeapMemory
     */
     static HeapMemory getJvmHeapMemoryInfo(){

        try {
            MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage heapMemoryUsage = memoryMBean.getHeapMemoryUsage();
            if(heapMemoryUsage!=null){
                HeapMemory memory = new HeapMemory();
                memory.setCommitted(heapMemoryUsage.getCommitted());
                memory.setInit(heapMemoryUsage.getInit());
                memory.setMax(heapMemoryUsage.getMax());
                memory.setUsed(heapMemoryUsage.getUsed());
                return memory;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    /**
     *
     * @method:	TODO    获取非堆内存信息
     * @time  :	2018/3/27 9:22
     * @author:	黄鹤老板
     * @param
     * @return:     NonHeapMemory
     */
     static NonHeapMemory getJvmNonHeapMemoryInfo(){

        try {
            MemoryMXBean memoryMBean = ManagementFactory.getMemoryMXBean();
            MemoryUsage nonHeapMemoryUsage = memoryMBean.getNonHeapMemoryUsage();
            if(nonHeapMemoryUsage!=null){
                NonHeapMemory memory = new NonHeapMemory();
                memory.setCommitted(nonHeapMemoryUsage.getCommitted());
                memory.setInit(nonHeapMemoryUsage.getInit());
                memory.setMax(nonHeapMemoryUsage.getMax());
                memory.setUsed(nonHeapMemoryUsage.getUsed());
                return memory;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return  null;
    }


    /**
     *
     * @method:	TODO    获取JVM的内存信息
     * @time  :	2018/3/27 9:23
     * @author:	黄鹤老板
     * @param
     * @return:     MemoryInfo
     */
    static MemoryInfo getJvmMemoryInfo(){

        try {
            Runtime runtime = Runtime.getRuntime();
            MemoryInfo memoryInfo = new MemoryInfo();
            memoryInfo.setJvmFreeMemory(runtime.freeMemory());
            memoryInfo.setJvmMaxMemory(runtime.maxMemory());
            memoryInfo.setJvmTotalMemory(runtime.totalMemory());
            return memoryInfo;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @method:	TODO    提供内存信息查询接口
     * @time  :	2018/10/15 21:55
     * @author:	黄鹤老板
     * @param
     * @return:     java.util.Map<java.lang.String,com.hsamgle.basic.entity.MemoryInfo>
     */
    public static Map<String,MemoryInfo> getMemoryInfo(){

        Map<String,MemoryInfo> memoryInfo = new HashMap<>(3);
        memoryInfo.put("os",getJvmMemoryInfo());
        memoryInfo.put("Heap",getJvmHeapMemoryInfo());
        memoryInfo.put("NonHeap",getJvmNonHeapMemoryInfo());
        return memoryInfo;
    }


}
