package com.dynamic.route.config;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @author weijinsheng
 * @date 2017/11/9 17:54
 */
@Configuration
@EnableTransactionManagement
public class MybatisAutoConfiguration implements TransactionManagementConfigurer {

    private final static Logger log = LoggerFactory.getLogger(MybatisAutoConfiguration.class);

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource dataSource() {
        DataSource dataSource = null;
        try {
            dataSource = new org.apache.tomcat.jdbc.pool.DataSource();
            log.info("init datasource success");
        } catch (Exception e) {
            log.error("init datasource failed:" ,e);
        }
        return dataSource;
    }

    @Bean
    public SqlSessionFactory sqlSessionFactoryBean() {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource());

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        SqlSessionFactory sqlSessionFactory = null;
        try {
            sqlSessionFactoryBean.setMapperLocations(resolver.getResources("classpath:mybatis/**/*.xml"));
            sqlSessionFactory =  sqlSessionFactoryBean.getObject();
            log.info("init sqlSessionFactory success");
        } catch (Exception e) {
            log.error("init sqlSessionFactory fail :", e);
            e.printStackTrace();
        }

        return sqlSessionFactory;
    }

    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
