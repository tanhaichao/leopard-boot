package io.leopard.boot.web.test;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.mvc.mock.asserter.AssertController;
import io.leopard.boot.mvc.mock.asserter.AssertManager;

@RestController
@RequestMapping("/leopardboot/html/")
public class HtmlController implements AssertController {

	@RequestMapping("/list.html")
	public ModelAndView list(int start, int size) {
		ModelAndView view = new ModelAndView("test/html");
		return view;
	}

	@Override
	public void asserter(AssertManager assertManager) {
		// assertManager.add("/leopardboot/xparam/start?start=1").name("验证start是否为1").data(1);
	}

}