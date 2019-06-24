package com.hsamgle.mysql.config;

import com.github.pagehelper.PageInterceptor;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.tomcat.jdbc.pool.PoolProperties;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.StringUtils;

import javax.sql.DataSource;
import java.util.Properties;


/**
 *
 *  @feture   :	    TODO		Mybatis 初始化配置
 *	@file_name:	    MybatisAutoConfiguration.java
 * 	@packge:	    com.hsamgle.mysql.config
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 10:21
 *	@company:		江南皮革厂
 */
@Configuration
@EnableTransactionManagement
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MybatisAutoConfiguration {

    /** url 地址的固定头部 */
    private static final String URL_HEAD = "jdbc:mysql://";

    /** 默认的连接参数 */
    private static final String PARAMS = "?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=Asia/Shanghai&useSSL=false";

    /** 驱动 */
    private final static String DRIVER = "com.mysql.cj.jdbc.Driver";


    /** 默认的主机地址 */
    @Value("${mysql.host}")
    private String host;

    /** 数据库名称 */
    @Value("${mysql.dbname}")
    private String dbName;

    @Value("${mysql.username}")
    private String username;

    @Value("${mysql.password}")
    private String password;

    @Value("${mysql.aliases_package}")
    private String typeAliasesPackage;

    @Value("${mysql.mapperlocations}")
    private String mapperLocations;


    /**
     *
     * @method:	TODO    初始化连接池
     * @time  :	2018/3/27 10:21
     * @author:	黄鹤老板
     * @param
     * @return:     javax.sql.DataSource   这里的数据源使用tomcat jdbc
     */
    @Bean(name = "DataSource")
    public DataSource dataSource() {

        if (StringUtils.isEmpty(host)) {
            return null;
        }

        PoolProperties properties = new PoolProperties();
        properties.setDriverClassName(DRIVER);
        properties.setPassword(password);
        properties.setUrl(URL_HEAD + host + "/" + dbName + PARAMS);
        properties.setUsername(username);
        properties.setTestOnBorrow(true);
        properties.setTestWhileIdle(true);
        // 初始连接池大小为5
        properties.setInitialSize(5);
        properties.setMaxIdle(30);
        properties.setValidationQuery("select 1");
        // 10 seconds
        properties.setMaxWait(10000);
        properties.setRemoveAbandoned(true);
        properties.setRemoveAbandonedTimeout(30);
        // 清理连接周期
        properties.setTimeBetweenEvictionRunsMillis(900 * 1000);
        // 空闲连接的生存阀值,这个时间需要小于wait_timeout
        properties.setMinEvictableIdleTimeMillis(900 * 1000);
        return new org.apache.tomcat.jdbc.pool.DataSource(properties);
    }



    /**
     *
     * @method:	TODO        定义session factory
     * @time  :	2018/3/27 10:22
     * @author:	黄鹤老板
     * @param dataSource
     * @return:     org.apache.ibatis.session.SqlSessionFactory
     */
    @Bean(name = "sqlSessionFactory")
    @ConditionalOnBean(name = "DataSource")
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        SqlSessionFactoryBean factory = new PackagesSqlSessionFactoryBean();
        factory.setDataSource(dataSource);
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        factory.setTypeAliasesPackage(typeAliasesPackage);

        Resource[] resoures = resolver.getResources(mapperLocations);
        factory.setMapperLocations(resoures);

        // 只对slave 节点 添加分页插件
        Interceptor[] interceptors = new Interceptor[]{pageInterceptor()};
        factory.setPlugins(interceptors);
        return factory.getObject();
    }



    /**
     *
     * @method:	TODO    初始化分页的插件
     * @time  :	2018/3/27 10:22
     * @author:	黄鹤老板
     * @param
     * @return:     com.github.pagehelper.PageInterceptor
     */
    @Bean
    public PageInterceptor pageInterceptor() {
        PageInterceptor pageHelper = new PageInterceptor();
        Properties p = new Properties();
        p.setProperty("offsetAsPageNum", "true");
        p.setProperty("rowBoundsWithCount", "true");
        p.setProperty("reasonable", "true");
        p.setProperty("pageSizeZero", "true");
        p.setProperty("helperDialect", "mysql");
        pageHelper.setProperties(p);
        return pageHelper;
    }


    /**
     *
     * @method:	TODO    配置 mysql 事务管理，这里的事务只对master生效
     * @time  :	2018/3/27 10:23
     * @author:	黄鹤老板
     * @param dataSource
     * @return:     org.springframework.jdbc.transactional.DataSourceTransactionManager
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("DataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }



}
