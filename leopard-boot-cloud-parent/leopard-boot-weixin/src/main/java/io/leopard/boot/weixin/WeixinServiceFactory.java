package io.leopard.boot.weixin;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeixinServiceFactory {

	protected static Log logger = LogFactory.getLog(WeixinServiceFactory.class);
	@Value("${leopard.proxy:}") // 默认为empty
	private String proxy;// 格式 ip:port

	private static String PROXY;// 格式 ip:port

	@PostConstruct
	public void init() {
		logger.info("init:" + proxy);
		PROXY = proxy;
	}

	public static WeixinService create(String appId, String secret) {
		logger.info("PROXY:" + PROXY);
		WeixinServiceImpl weixinService = new WeixinServiceImpl();
		weixinService.setAppId(appId);
		weixinService.setSecret(secret);
		weixinService.setProxy(PROXY);
		weixinService.init();
		return weixinService;
	}

}
