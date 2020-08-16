package io.leopard.test;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootTest(classes = LeopardTestApplication.class)
@RunWith(LeopardRunner.class)
@ActiveProfiles("dev")
@EnableTransactionManagement
// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTests {
	static {

		// System.setProperty(LoggingSystem.class.getName(), LeopardTestLogbackLoggingSystem.class.getName());

	}
}
