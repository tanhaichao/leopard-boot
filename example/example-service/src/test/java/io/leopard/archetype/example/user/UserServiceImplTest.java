package io.leopard.archetype.example.user;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;

public class UserServiceImplTest extends IntegrationTests {

	@Autowired
	private UserServiceImpl userService;

	@Test
	public void get() {
		User user = userService.get(1);
		Json.printFormat(user, "user");
	}

}