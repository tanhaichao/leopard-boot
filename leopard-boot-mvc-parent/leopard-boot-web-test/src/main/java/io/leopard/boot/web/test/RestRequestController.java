package io.leopard.boot.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

/**
 * REST
 * 
 * @author 谭海潮
 *
 */
@RestController
@RequestMapping("/leopardboot/RestRequest/")
public class RestRequestController implements AssertController {

	@RequestMapping("/string")
	public String string(int uid) {
		return "uid:" + uid;
	}

	@RequestMapping(value = "jsonString", produces = "application/json;charset=UTF-8")
	@ResponseBody
	public String jsonString(int uid) {
		return "uid:" + uid;
	}

	@Override
	public void asserter(AssertManager assertManager) {
	}

}
