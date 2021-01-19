package io.leopard.archetype.example.web.controller.demo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.basicauth.BasicAuth;

@Controller
@RequestMapping("/auth/")
public class BasicAuthController {

	@RequestMapping
	@ResponseBody
	@BasicAuth
	public String get(long uid) {
		return "hello:" + uid;
	}
}
