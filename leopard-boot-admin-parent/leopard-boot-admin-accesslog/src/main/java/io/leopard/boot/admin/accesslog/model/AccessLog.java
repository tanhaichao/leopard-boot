package io.leopard.boot.admin.accesslog.model;

/**
 * 访问日志
 * 
 * @author 谭海潮
 *
 */
public class AccessLog {

	/**
	 * 访问日志ID
	 */
	private long accessLogId;

	/**
	 * 管理员ID
	 */
	private long adminId;
	/**
	 * 角色ID
	 */
	private long roleId;
	/**
	 * IP
	 */
	private String proxyIp;

	/**
	 * 请求地址
	 */
	private String url;

	/**
	 * 请求方法
	 */
	private String requestMethod;

	private String handlerName;
	private String parameters;

	private String requestBody;

	private String responseBody;

	/**
	 * 异常堆栈
	 */
	private String exception;

	public long getAccessLogId() {
		return accessLogId;
	}

	public void setAccessLogId(long accessLogId) {
		this.accessLogId = accessLogId;
	}

	public long getAdminId() {
		return adminId;
	}

	public void setAdminId(long adminId) {
		this.adminId = adminId;
	}

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public String getProxyIp() {
		return proxyIp;
	}

	public void setProxyIp(String proxyIp) {
		this.proxyIp = proxyIp;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getRequestBody() {
		return requestBody;
	}

	public void setRequestBody(String requestBody) {
		this.requestBody = requestBody;
	}

	public String getResponseBody() {
		return responseBody;
	}

	public void setResponseBody(String responseBody) {
		this.responseBody = responseBody;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getRequestMethod() {
		return requestMethod;
	}

	public void setRequestMethod(String requestMethod) {
		this.requestMethod = requestMethod;
	}

	public String getHandlerName() {
		return handlerName;
	}

	public void setHandlerName(String handlerName) {
		this.handlerName = handlerName;
	}
}
