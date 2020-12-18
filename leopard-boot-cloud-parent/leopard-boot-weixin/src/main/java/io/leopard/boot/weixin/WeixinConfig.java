package io.leopard.boot.weixin;

public class WeixinConfig {
	private String appId;

	private String secret;

	private String proxy;// 格式 ip:port

	public WeixinConfig() {
	}

	public WeixinConfig(String appId, String secret, String proxy) {
		this.appId = appId;
		this.secret = secret;
		this.proxy = proxy;
	}

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getProxy() {
		return proxy;
	}

	public void setProxy(String proxy) {
		this.proxy = proxy;
	}

}
