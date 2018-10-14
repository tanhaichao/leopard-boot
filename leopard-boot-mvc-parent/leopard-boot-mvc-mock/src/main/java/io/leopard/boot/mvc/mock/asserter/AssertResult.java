package io.leopard.boot.mvc.mock.asserter;

/**
 * 断言结果
 * 
 * @author 谭海潮
 *
 */
public class AssertResult {

	/**
	 * 控制器类名
	 */
	private String controller;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 预期，true:断言成功，false:为断言失败
	 */
	private boolean expect;

	/**
	 * 状态
	 */
	private String status;

	/**
	 * 断言消息
	 */
	private String message;

	public String getController() {
		return controller;
	}

	public void setController(String controller) {
		this.controller = controller;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public boolean isExpect() {
		return expect;
	}

	public void setExpect(boolean expect) {
		this.expect = expect;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
