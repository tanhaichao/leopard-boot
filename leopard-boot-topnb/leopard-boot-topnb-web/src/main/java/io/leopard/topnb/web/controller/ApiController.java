package io.leopard.topnb.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import io.leopard.topnb.TopnbBeanFactory;
import io.leopard.topnb.methodtime.MethodTimeHandler;

@Controller("topnbApiController")
@RequestMapping("/topnb/")
public class ApiController {

	private static MethodTimeHandler performanceHandler = TopnbBeanFactory.getPerformanceHandler();

	/**
	 * 清空数据
	 * 
	 * @return
	 */
	@RequestMapping
	public boolean clear() {
		performanceHandler.clear();
		return true;
	}
}
