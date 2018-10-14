package io.leopard.redis.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis属性配置
 * 
 * @author 谭海潮
 *
 */
@ConfigurationProperties(prefix = "app.redis")
public class RedisProperties {
	private String host;

	private Integer port;

	private String password;

	private Integer maxActive;

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Integer getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(Integer maxActive) {
		this.maxActive = maxActive;
	}

	public String parseServer() {
		String server = this.host + ":" + this.port;
		if (password != null && password.length() > 0) {
			server += ":" + password;
		}
		return server;
	}
}
