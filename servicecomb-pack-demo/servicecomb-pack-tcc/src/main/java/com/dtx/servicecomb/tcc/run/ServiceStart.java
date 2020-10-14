package com.dtx.servicecomb.tcc.run;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
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
@EnableTransactionManagement
@ComponentScan(basePackages = {"com.dtx.servicecomb.tcc.service","com.dtx.servicecomb.tcc.controller"})
@MapperScan("com.dtx.servicecomb.tcc.dao")
public class ServiceStart {

	public static void main(String[] args) {
		SpringBootstrap.run(ServiceStart.class, args);
	}
}
