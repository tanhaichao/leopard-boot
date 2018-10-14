package io.leopard.boot.web.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import io.leopard.boot.mvc.mock.MockWeb;
import io.leopard.boot.mvc.mock.asserter.AssertControllerManager;
import io.leopard.boot.mvc.mock.asserter.AssertManager;
import io.leopard.boot.mvc.mock.asserter.AssertResult;
import io.leopard.boot.mvc.mock.asserter.Asserter;

@RestController
@RequestMapping("/leopardboot/assert/")
@ConditionalOnProperty(name = "assert", havingValue = "true")
public class AssertController {
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private AssertControllerManager assertControllerManager;

	private MockWeb web;

	@PostConstruct
	public void setUp() throws Exception {
		web = new MockWeb(context);
	}

	@RequestMapping("all.html")
	// @ResponseBody
	public ModelAndView html() {
		List<AssertResult> resultList = new ArrayList<>();
		for (AssertManager assertManager : assertControllerManager.getAssertManagerList()) {
			for (Asserter asserter : assertManager.getAsserterList()) {
				AssertResult result = new AssertResult();
				result.setController(assertManager.getController().getClass().getSimpleName());
				result.setName(asserter.getName());
				result.setExpect(asserter.isExpect());

				boolean flag;
				try {
					web.perform(assertManager.getController(), asserter);
					result.setMessage("success");
					flag = true;
				}
				catch (Throwable e) {
					flag = false;
					// e.printStackTrace();//TODO
					result.setMessage(e.getMessage());
				}
				if (asserter.isExpect() == flag) {
					result.setStatus("success");
				}
				else {
					result.setStatus("error");
				}

				resultList.add(result);
			}
		}
		ModelAndView view = new ModelAndView("leopard-boot-web-test/assert");
		view.addObject("resultList", resultList);
		System.err.println("all html view:" + view.getViewName());
		return view;
	}

}
