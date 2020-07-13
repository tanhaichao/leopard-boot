package io.leopard.boot.weixin;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
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
		if (PROXY == null) {
			throw new RuntimeException("WeixinServiceFactory未初始化");
		}
		logger.info("PROXY:" + PROXY);
		return create(appId, secret, PROXY);
	}

	public static WeixinService create(String appId, String secret, String proxy) {
		WeixinServiceImpl weixinService = new WeixinServiceImpl();
		weixinService.setAppId(appId);
		weixinService.setSecret(secret);
		weixinService.setProxy(proxy);
		weixinService.init();
		return weixinService;
	}

}
