package io.leopard.boot.web.test;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

@RestController
@RequestMapping("/leopardboot/xparam/")
public class XparamController implements AssertController {

	@RequestMapping("/list")
	@ResponseBody
	public String list(int start, int size) {
		return "start:" + start + " size:" + size;
	}

	@RequestMapping(value = "/start", produces = { MediaType.APPLICATION_JSON_VALUE })
	@ResponseBody
	public int start(int start) {
		return start;
	}

	@Override
	public void asserter(AssertManager assertManager) {
		assertManager.add("/leopardboot/xparam/start?start=1").name("验证start是否为1").data(1);
		
		assertManager.add("/leopardboot/xparam/start?start=1").name("验证start是否不为0").data(0).expect(false);
		assertManager.add("/leopardboot/xparam/start?start=0").name("验证start是否为0").data(0).expect(true);
		assertManager.add("/leopardboot/xparam/list?pageId=2").data("start:10 size:10");
	}

}