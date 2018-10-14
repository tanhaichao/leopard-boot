package io.leopard.boot.web.test;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * 请求映射
 * 
 * @author 谭海潮
 *
 */
@RestController
@RequestMapping("/leopardboot/RequestMapping/")
@ConditionalOnProperty(name = "test", havingValue = "true")
public class RequestMappingController {

	@RequestMapping
	@ResponseBody
	public String get() {
		return "get";
	}

	@RequestMapping("get2")
	@ResponseBody
	public String get2() {
		return "get2";
	}
}