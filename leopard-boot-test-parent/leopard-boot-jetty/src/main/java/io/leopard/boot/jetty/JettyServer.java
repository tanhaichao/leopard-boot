package io.leopard.boot.jetty;

import org.apache.commons.lang3.SystemUtils;
import org.springframework.boot.SpringApplication;

public class JettyServer {

	public static void run(Class<?> application) {
		run(application, 80);
	}

	public static void start() {
		run();
	}

	public static void run() {
		run(80);
	}

	public static void run(int port) {
		Class<?> application;
		try {
			application = Class.forName("io.leopard.boot.LeopardApplication");
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
		run(application, port);
	}

	public static void run(Class<?> application, int port) {
		if (port == 80) {
			if (SystemUtils.IS_OS_MAC) {
				port = 8080;
			}
		}

		System.setProperty("server.port", Integer.toString(port));

		SpringApplication app = new SpringApplication(application);
		// 启动dev配置文件
		app.setAdditionalProfiles("dev"); // dev 或prod
		app.run();
	}
}
