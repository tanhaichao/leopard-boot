package io.leopard.boot.archetype.example.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 首页
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping
public class IndexController {

	/**
	 * 首页
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public String index() {
		return "Hello Leopard!";
	}
}
