package io.leopard.web.mvc.option;

import java.util.Date;
import java.util.Set;

import io.leopard.spring.ServerEnv;

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
	private ServerEnv env;
	/**
	 * 启动时间
	 */
	private Date startupTime;
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

	public ServerEnv getEnv() {
		return env;
	}

	public void setEnv(ServerEnv env) {
		this.env = env;
	}

	public Date getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(Date startupTime) {
		this.startupTime = startupTime;
	}

	public Set<String> getProfileSet() {
		return profileSet;
	}

	public void setProfileSet(Set<String> profileSet) {
		this.profileSet = profileSet;
	}
}
