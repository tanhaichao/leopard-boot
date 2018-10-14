package io.leopard.boot.mvc.mock.asserter;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

public class AssertControllerManager {

	@Autowired(required = false)
	private List<AssertController> assertControllerList;

	private final List<AssertManager> assertManagerList = new ArrayList<>();

	@PostConstruct
	public void init() {
		if (assertControllerList != null) {
			for (AssertController controller : assertControllerList) {
				AssertManager assertManager = new AssertManager(controller);
				controller.asserter(assertManager);
				assertManagerList.add(assertManager);
			}
		}
	}

	public List<AssertManager> getAssertManagerList() {
		return assertManagerList;
	}
}
