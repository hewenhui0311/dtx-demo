package com.dtx.seata.at;

import javax.sql.DataSource;

import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import io.seata.rm.datasource.DataSourceProxy;

@Configuration
@AutoConfigureAfter({DataSourceAutoConfiguration.class})
@AutoConfigureBefore({MybatisAutoConfiguration.class})
public class DataSourceProxyConfig {
	
	/*
	 * @Bean
	 * 
	 * @ConfigurationProperties(prefix = "spring.datasource") public DruidDataSource
	 * druidDataSource() { return new DruidDataSource(); }
	 */
	
    @Bean
    @Primary
    public DataSourceProxy dataSourceProxy(DataSource druidDataSource) {
        return new DataSourceProxy(druidDataSource);
    }
}
