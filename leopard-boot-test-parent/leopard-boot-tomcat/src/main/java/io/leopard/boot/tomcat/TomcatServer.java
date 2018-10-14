package io.leopard.boot.tomcat;

import org.springframework.boot.SpringApplication;

public class TomcatServer {

	public static void run(Class<?> application) {
		run(application, 80);
	}

	public static void run(Class<?> application, int port) {
		System.setProperty("server.port", Integer.toString(port));

		SpringApplication app = new SpringApplication(application);
		// 启动dev配置文件
		app.setAdditionalProfiles("dev"); // dev 或prod
		app.run();
	}
}
