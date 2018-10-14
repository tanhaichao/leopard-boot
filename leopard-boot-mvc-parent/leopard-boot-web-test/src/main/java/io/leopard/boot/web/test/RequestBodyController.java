package io.leopard.boot.web.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

/**
 * RequestBody
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/leopardboot/RequestBody/")
public class RequestBodyController implements AssertController {

	@RequestMapping
	@ResponseBody
	public int uid(int uid, @RequestBody(required = false) User user) {
		if (user != null) {
			return user.getUid();
		}
		else {
			return uid;
		}
	}

	public static class User {
		int uid;

		public int getUid() {
			return uid;
		}

		public void setUid(int uid) {
			this.uid = uid;
		}

	}

	@Override
	public void asserter(AssertManager assertManager) {
		assertManager.add("/leopardboot/RequestBody/uid").requestBody("{\"uid\":2}").name("验证uid为1").data(1);
		assertManager.add("/leopardboot/RequestBody/uid").name("uid默认为0").data(0);
		assertManager.add("/leopardboot/RequestBody/uid").name("uid默认为0").data(2);
		assertManager.add("/leopardboot/RequestBody/uid").requestBody("{\"uid\":3}").name("uid默认为0").data(3);
	}

}
