package io.leopard.boot.server.web.controller;

import java.util.Date;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.server.web.vo.ServerInfoVO;
import io.leopard.boot.snowflake.SnowflakeUtil;
import io.leopard.boot.util.ServerUtil;
import io.leopard.redis.Redis;
import io.leopard.spring.ServerEnv;
import io.leopard.spring.SpringEnvUtil;

/**
 * 服务器信息
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/leopard/server/")
public class ServerInfoController {
	/**
	 * 启动时间
	 */
	private static Date startupTime = new Date();

	private static final String KEY = "TIMER_LOCKER";

	@Autowired
	private Redis redis;

	protected String getJobServerIp() {
		return redis.get(KEY);
	}

	/**
	 * 获取服务器信息
	 */
	@RequestMapping
	@ResponseBody
	public ServerInfoVO info() {
		TimeZone timeZone = TimeZone.getDefault();
		String serverIp = ServerUtil.getServerIp();
		ServerEnv env = SpringEnvUtil.getEnv();
		int workerId = SnowflakeUtil.getWorkerId();
		String jobServerIp = this.getJobServerIp();

		ServerInfoVO serverInfo = new ServerInfoVO();
		serverInfo.setServerIp(serverIp);
		serverInfo.setEnv(env);
		serverInfo.setStartupTime(startupTime);
		serverInfo.setProfileSet(SpringEnvUtil.getProfileSet());
		serverInfo.setTimeZoneId(timeZone.getID());
		serverInfo.setWorkerId(workerId);
		serverInfo.setJobServerIp(jobServerIp);
		return serverInfo;
	}

}
