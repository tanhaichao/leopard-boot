package io.leopard.archetype.example.web.controller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.company.example.web.vo.UserVO;

import io.leopard.json.Json;
import io.leopard.test.IntegrationTests;

public class UserControllerTest extends IntegrationTests {

	@Autowired
	private UserController userController;

	@Test
	public void get() {
		UserVO user = userController.get(1);
		Json.printFormat(user, "user");
	}

}