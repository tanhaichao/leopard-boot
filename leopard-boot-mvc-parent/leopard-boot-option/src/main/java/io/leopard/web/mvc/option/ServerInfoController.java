package io.leopard.web.mvc.option;

import java.util.Date;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.util.ServerUtil;
import io.leopard.spring.ServerEnv;
import io.leopard.spring.SpringEnvUtil;

/**
 * 服务器信息
 * 
 * @author 谭海潮
 *
 */
@Controller
public class ServerInfoController {
	/**
	 * 启动时间
	 */
	private static Date startupTime = new Date();

	/**
	 * 获取服务器信息
	 */
	@RequestMapping("serverInfo")
	@ResponseBody
	public ServerInfoVO serverInfo() {
		String serverIp = ServerUtil.getServerIp();
		ServerEnv env = SpringEnvUtil.getEnv();
		ServerInfoVO serverInfo = new ServerInfoVO();
		serverInfo.setServerIp(serverIp);
		serverInfo.setEnv(env);
		serverInfo.setStartupTime(startupTime);
		serverInfo.setProfileSet(SpringEnvUtil.getProfileSet());
		return serverInfo;
	}

}