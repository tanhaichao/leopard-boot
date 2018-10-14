package io.leopard.boot.mvc.mock.asserter;

import java.util.ArrayList;
import java.util.List;

/**
 * 断言管理器
 * 
 * @author 谭海潮
 *
 */
public class AssertManager {

	private final List<Asserter> asserterList = new ArrayList<>();

	final AssertController controller;

	public AssertManager(AssertController controller) {
		this.controller = controller;
	}

	public Asserter add(String url) {
		Asserter asserter = new Asserter();
		asserter.setUrl(url);
		asserterList.add(asserter);
		return asserter;
	}

	public List<Asserter> getAsserterList() {
		return asserterList;
	}

	public AssertController getController() {
		return controller;
	}

}
