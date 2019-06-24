package com.hsamgle.basic.test;

import com.hsamgle.basic.application.BaseApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 *
 *  @feture   :	    TODO		单元测试父类
 *	@file_name:	    BaseTest.java
 * 	@packge:	    com.hsamgle.basic.test
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:34
 *	@company:		江南皮革厂
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = BaseApplication.class)
public class BaseTest extends AbstractJUnit4SpringContextTests {



}