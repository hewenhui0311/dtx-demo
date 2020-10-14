package com.dtx.servicecomb.saga.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.dtx.demo.common.boot.SpringBootstrap;

/**
 * @auth hewenhui
 * @data 2020-9-19
 * @since
 * @description
 */
@SpringBootApplication(scanBasePackages = { "com.dtx.servicecomb.saga.service", "com.dtx.servicecomb.saga.controller" })
//@ComponentScan(basePackages = {"com.dtx.servicecomb.saga.service","com.dtx.servicecomb.saga.controller"})
@EnableDiscoveryClient
@EnableTransactionManagement
@MapperScan("com.dtx.servicecomb.saga.dao")
public class ServiceStart {

	public static void main(String[] args) {
		SpringBootstrap.run(ServiceStart.class, args);
	}

}
