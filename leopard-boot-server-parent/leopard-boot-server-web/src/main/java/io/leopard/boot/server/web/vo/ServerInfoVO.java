package io.leopard.boot.server.web.vo;

import java.util.Date;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	 * profiles
	 */
	private Set<String> profileSet;
	/**
	 * 启动时间
	 */
	private Date startupTime;

	/**
	 * 时区
	 */
	private String timeZoneId;

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

	public Set<String> getProfileSet() {
		return profileSet;
	}

	public void setProfileSet(Set<String> profileSet) {
		this.profileSet = profileSet;
	}

	public String getTimeZoneId() {
		return timeZoneId;
	}

	public void setTimeZoneId(String timeZoneId) {
		this.timeZoneId = timeZoneId;
	}

	public Date getStartupTime() {
		return startupTime;
	}

	public void setStartupTime(Date startupTime) {
		this.startupTime = startupTime;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getFormattedStartupTime() {
		return this.startupTime;
	}
}
