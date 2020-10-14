package com.dtx.seata.saga;

import java.io.IOException;
import java.util.Optional;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.scheduling.concurrent.ThreadPoolExecutorFactoryBean;

import io.seata.saga.engine.StateMachineConfig;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.engine.config.DbStateMachineConfig;
import io.seata.saga.engine.impl.ProcessCtrlStateMachineEngine;
import io.seata.saga.rm.StateMachineEngineHolder;
import io.seata.spring.boot.autoconfigure.StarterConstants;
import io.seata.spring.boot.autoconfigure.properties.SpringCloudAlibabaConfiguration;

@AutoConfigureAfter(SpringCloudAlibabaConfiguration.class)
@ConfigurationProperties(prefix = StarterConstants.SEATA_SPRING_CLOUD_ALIBABA_PREFIX + ".saga")
public class SagaAutoConfiguration {

	private String[] resources;
	private boolean enableAsyn = false;
	private String threadNamePrefix;
	private int threadPoolCoreSize = 1;
	private int threadPoolMaxSize = 10;

	private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

	@Autowired
	private SpringCloudAlibabaConfiguration aliConfig;

	@Bean(initMethod = "initialize", destroyMethod = "destroy")
	public ThreadPoolExecutorFactoryBean threadPoolExecutorFactory() {
		ThreadPoolExecutorFactoryBean threadPoolExecutorFactoryBean = new ThreadPoolExecutorFactoryBean();
		threadPoolExecutorFactoryBean.setThreadNamePrefix(threadNamePrefix);
		threadPoolExecutorFactoryBean.setCorePoolSize(threadPoolCoreSize);
		threadPoolExecutorFactoryBean.setMaxPoolSize(threadPoolMaxSize);
		return threadPoolExecutorFactoryBean;
	}

	@Bean(destroyMethod = "destroy")
	@ConditionalOnBean(DataSource.class)
	@ConditionalOnMissingBean(StateMachineConfig.class)
	public DbStateMachineConfig stateMachineConfig(DataSource dataSource,
			ThreadPoolExecutorFactoryBean threadPoolExecutorFactory) {
		DbStateMachineConfig config = new DbStateMachineConfig();
		config.setDataSource(dataSource);
		config.setResources(resolveResources());
		config.setEnableAsync(enableAsyn);
		config.setThreadPoolExecutor((ThreadPoolExecutor) threadPoolExecutorFactory.getObject());
		config.setApplicationId(aliConfig.getApplicationId());
		config.setTxServiceGroup(aliConfig.getTxServiceGroup());
		return config;
	}

	@Bean
	@ConditionalOnMissingBean(StateMachineEngine.class)
	public StateMachineEngine stateMachineEngine(StateMachineConfig stateMachineConfig) {
		ProcessCtrlStateMachineEngine stateMachineEngine = new ProcessCtrlStateMachineEngine();
		stateMachineEngine.setStateMachineConfig(stateMachineConfig);
		return stateMachineEngine;
	}

	@Bean
	@ConditionalOnBean(StateMachineEngine.class)
	public StateMachineEngineHolder stateMachineEngineHolder(StateMachineEngine stateMachineEngine) {
		StateMachineEngineHolder holder = new StateMachineEngineHolder();
		holder.setStateMachineEngine(stateMachineEngine);
		return holder;
	}

	public String[] getResources() {
		return resources;
	}

	public void setResources(String[] resources) {
		this.resources = resources;
	}

	public boolean isEnableAsyn() {
		return enableAsyn;
	}

	public void setEnableAsyn(boolean enableAsyn) {
		this.enableAsyn = enableAsyn;
	}

	public String getThreadNamePrefix() {
		return threadNamePrefix;
	}

	public void setThreadNamePrefix(String threadNamePrefix) {
		this.threadNamePrefix = threadNamePrefix;
	}

	public int getThreadPoolCoreSize() {
		return threadPoolCoreSize;
	}

	public void setThreadPoolCoreSize(int threadPoolCoreSize) {
		this.threadPoolCoreSize = threadPoolCoreSize;
	}

	public int getThreadPoolMaxSize() {
		return threadPoolMaxSize;
	}

	public void setThreadPoolMaxSize(int threadPoolMaxSize) {
		this.threadPoolMaxSize = threadPoolMaxSize;
	}

	public Resource[] resolveResources() {
		return Stream.of(Optional.ofNullable(this.resources).orElse(new String[0]))
				.flatMap(resource -> Stream.of(getResources(resource))).toArray(Resource[]::new);
	}

	private Resource[] getResources(String location) {
		try {
			return resourceResolver.getResources(location);
		} catch (IOException e) {
			return new Resource[0];
		}
	}
}
