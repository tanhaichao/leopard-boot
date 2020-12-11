package io.leopard.web.mvc.option;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 服务器信息
 * 
 * @author 谭海潮
 *
 */
@Controller
public class EnvServerController {

	/**
	 * 获取服务器信息
	 */
	@RequestMapping("serverInfo")
	@ResponseBody
	public ServerInfoVO serverInfo() {
		ServerInfoVO serverInfo = new ServerInfoVO();
		return serverInfo;
	}

}
