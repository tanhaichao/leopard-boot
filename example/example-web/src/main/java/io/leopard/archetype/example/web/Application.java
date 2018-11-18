package io.leopard.archetype.example.web;

import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.ComponentScan;

import io.leopard.boot.LeopardApplication;

@ComponentScan(basePackages = { "com.company.example" })
public class Application extends LeopardApplication {

	public static void main(String[] args) {
		boolean isProd = isProd(args);
		SpringApplication app = new SpringApplication(Application.class);
		// 启动dev配置文件
		if (isProd) {
			app.setAdditionalProfiles("dev"); // dev 或prod
		}
		app.run();
	}

	protected static boolean isProd(String[] args) {
		// --spring.profiles.active=prod
		for (String arg : args) {
			if ("--spring.profiles.active=prod".equals(arg)) {
				return true;
			}
		}
		return false;
	}

}
