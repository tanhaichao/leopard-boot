package io.leopard.boot.xparam;

import javax.servlet.http.HttpServletRequest;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;

/**
 * 获取request的serverName
 * 
 * @author 阿海
 * 
 */
@Service
public class ServerNameXParam implements XParam {

	@Override
	public Object getValue(HttpServletRequest request, MethodParameter parameter) {
		String serverName = request.getServerName();
		return serverName;
	}

	@Override
	public String getKey() {
		return "serverName";
	}

}
