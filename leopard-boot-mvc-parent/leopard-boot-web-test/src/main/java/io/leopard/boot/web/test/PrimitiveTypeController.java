package io.leopard.boot.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

/**
 * 原始数据类型测试
 * 
 * @author 谭海潮
 *
 */
@RestController
@RequestMapping("/leopardboot/PrimitiveType/")
public class PrimitiveTypeController implements AssertController {

	@RequestMapping
	@ResponseBody
	public int uid(int uid) {
		return uid;
	}

	@Override
	public void asserter(AssertManager assertManager) {
		assertManager.add("/leopardboot/PrimitiveType/uid?uid=1").name("验证uid为1").data(1);
		assertManager.add("/leopardboot/PrimitiveType/uid").name("uid默认为0").data(0);
	}

}
