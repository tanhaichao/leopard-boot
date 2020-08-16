package io.leopard.test;

import org.junit.runners.model.InitializationError;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;

public class LeopardRunner extends SpringJUnit4ClassRunner {

	public LeopardRunner(Class<?> clazz) throws InitializationError {
		super(clazz);
	}

	static {
		config();
	}

	public static void config() {
		System.err.println("config logback");
		LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();
		Logger root = context.getLogger("root");

		context.getStatusManager().clear();
		root.setLevel(Level.WARN);
	}
}
