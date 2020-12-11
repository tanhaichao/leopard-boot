package io.leopard.web.mvc.option;

import java.util.Set;

/**
 * 服务器信息
 * 
 * @author 谭海潮
 *
 */
public class ServerInfoVO {

	/**
	 * 服务器IP
	 */
	private String serverIp;

	/**
	 * 当前环境
	 */
	private String env;

	/**
	 * profiles
	 */
	private Set<String> profileSet;

	public String getServerIp() {
		return serverIp;
	}

	public void setServerIp(String serverIp) {
		this.serverIp = serverIp;
	}

	public String getEnv() {
		return env;
	}

	public void setEnv(String env) {
		this.env = env;
	}

	public Set<String> getProfileSet() {
		return profileSet;
	}

	public void setProfileSet(Set<String> profileSet) {
		this.profileSet = profileSet;
	}
}
