package com.dtx.demo.common.boot;

import java.util.Objects;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringBootstrap extends AbstractBootstrap {
	
	private ConfigurableApplicationContext context;
	
	private static SpringBootstrap instance;

	private SpringBootstrap() {}
	
	public static SpringBootstrap getInstance() {
		if(instance == null) {
			synchronized (SpringBootstrap.class) {
				if(instance == null) {
					instance = new SpringBootstrap();
				}
			}
		}
		return instance;
	}
	
	public static void run(Class<?> clazz, String[] args) {
		SpringBootstrap boot = SpringBootstrap.getInstance();
		boot.setClazz(clazz);
		boot.setArgs(args);
		if(args.length == 0) {
			SpringBootstrap.getInstance().start();
		} else {
			switch (args.length) {
				case 1: if(args[0].equals("start")) 
							SpringBootstrap.getInstance().start(); 
						else 
							SpringBootstrap.getInstance().sendClosingSignal();
					break;
				case 2: SpringBootstrap.getInstance().setServiceName(args[0]); 
						if(args[1].equals("start")) 
							SpringBootstrap.getInstance().start();
						else
							SpringBootstrap.getInstance().sendClosingSignal();
					break;
				default: break;
			}
		}
	}
	
	@Override
	protected void startSpringContainer(Class<?> clazz, String[] args) {
		SpringApplication application = new SpringApplication(clazz);
		application.setBannerMode(Banner.Mode.LOG);
		context = application.run(args);
	}

	@Override
	protected void closeSpringContainer() {
		if(!Objects.isNull(context)) {	
			context.stop();
			context.close();
		}
	}

}
