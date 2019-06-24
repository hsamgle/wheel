package com.hsamgle.startup;

import com.hsamgle.mongodb.MongoDB;
import com.hsamgle.redis.client.single.SingleJedisPool;
import com.hsamgle.basic.utils.LogUtil;
import com.hsamgle.basic.utils.ThreadPoolExecutors;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

/**
 * @类功能: TODO    监听项目启动完成的监听器
 * @文件名: ApplicationStartup.java
 * @所在包: com.com.dplus.d.startup
 * @开发者: 黄先国
 * @邮_件: hsamgle@qq.com
 * @时_间: 2016年7月19日下午2:42:48
 * @公_司: 广州讯动网络科技有限公司
 */
@Component
public class ApplicationStartup implements ApplicationListener<ContextRefreshedEvent> {

    // 标识系统是否启动
    private static boolean startFlag = false;


    /**
     *
     * @方法功能：	TODO    JVM启动完成后，对组件有选择性的初始化
     * @编写时间：	2018/3/25 16:24
     * @author：	黄先国 | hsamgle@qq.com
     * * @param event
     * @return     void
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        try {
            if (!startFlag) {
                // 初始化线程池
                Assert.isTrue(ThreadPoolExecutors.init(),"初始化线程池");

                // 初始化redis的连接服务
                Assert.isTrue(SingleJedisPool.init(), "redis 初始化失败");

                // 初始化mongodb
                Assert.isTrue(MongoDB.init(), "MongoDB 初始化失败");

                //更改标识，防止二次启动
                startFlag = true;
            }

        } catch (Exception e) {
            LogUtil.error(e);
            System.err.println("####################################################");
            System.err.println("##################   启动失败   ####################");
            System.err.println("####################################################");
            System.exit(1);
        }
    }
}
