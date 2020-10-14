package com.dtx.seata.saga.run;

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
@ComponentScan(basePackages = {"com.dtx.seata.saga.service","com.dtx.seata.saga.controller"})
@MapperScan("com.dtx.seata.saga.dao")
@EnableTransactionManagement
@EnableDiscoveryClient
public class ServiceStart {

	public static void main(String[] args) {
		SpringBootstrap.run(ServiceStart.class, args);
	}
}
