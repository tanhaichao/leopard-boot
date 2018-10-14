package io.leopard.boot.mvc.mock.asserter;

/**
 * 断言者
 * 
 * @author 谭海潮
 *
 */
public class Asserter {

	/**
	 * 请求方式
	 */
	private String httpMethod = "GET";

	/**
	 * 域名
	 */
	private String domain;

	/**
	 * 地址
	 */
	private String url;

	/**
	 * body
	 */
	private String body;

	private String requestBody;

	/**
	 * 数据
	 */
	private Object data = "None";

	/**
	 * 状态码
	 */
	private String status;

	/**
	 * 错误消息
	 */
	private String message;

	/**
	 * 断言器名称
	 */
	private String name;

	/**
	 * 预期，默认为断言成功。true:断言成功，false:为断言失败
	 */
	private boolean expect = true;

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Asserter data(Object data) {
		this.data = data;
		return this;
	}

	public String getHttpMethod() {
		return httpMethod;
	}

	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Asserter name(String name) {
		this.name = name;
		return this;
	}

	public boolean isExpect() {
		return expect;
	}

	public void setExpect(boolean expect) {
		this.expect = expect;
	}

	public Asserter expect(boolean expect) {
		this.expect = expect;
		return this;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public Asserter requestBody(String requestBody) {
		this.requestBody = requestBody;
		return this;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}
}
