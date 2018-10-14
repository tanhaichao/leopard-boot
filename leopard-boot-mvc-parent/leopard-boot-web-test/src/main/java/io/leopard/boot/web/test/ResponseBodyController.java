package io.leopard.boot.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

/**
 * ResponseBody
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/leopardboot/ResponseBody/")
public class ResponseBodyController implements AssertController {

	@RequestMapping("/string")
	@ResponseBody
	public String string(int uid) {
		return "uid:" + uid;
	}

	@RequestMapping(value = "jsonString", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String jsonString(int uid) {
		return "uid:" + uid;
	}

	@RequestMapping(value = "xmlString", produces = "application/xml;charset=UTF-8")
	@ResponseBody
	public String xmlString(int uid) {
		return "uid:" + uid;
	}

	@RequestMapping("/jsonString2")
	@ResponseBody
	public String jsonString2(int uid) {
		return "uid:" + uid;
	}

	@Override
	public void asserter(AssertManager assertManager) {
		assertManager.add("/leopardboot/ResponseBody/string").requestBody("{\"uid\":3}").name("uid默认为0").data("uid:3");
	}

}
