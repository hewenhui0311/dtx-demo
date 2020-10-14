package com.dtx.seata.tcc.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dtx.demo.common.boot.SpringBootstrap;

/**
 * @auth hewenhui
 * @data 2020-9-19
 * @since 
 * @description
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.dtx.seata.tcc.service","com.dtx.seata.tcc.controller"})
@MapperScan("com.dtx.seata.tcc.dao")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ServiceStart {

	public static void main(String[] args) {
		SpringBootstrap.run(ServiceStart.class, args);
	}
}
