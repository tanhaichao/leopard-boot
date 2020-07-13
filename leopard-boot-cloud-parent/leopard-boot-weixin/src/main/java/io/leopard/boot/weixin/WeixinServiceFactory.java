package io.leopard.boot.weixin;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WeixinServiceFactory {

	@Value("${leopard.proxy:}") // 默认为empty
	private String proxy;// 格式 ip:port

	private static String PROXY;// 格式 ip:port

	@PostConstruct
	public void init() {
		PROXY = proxy;
	}

	public static WeixinService create(String appId, String secret) {
		WeixinServiceImpl weixinService = new WeixinServiceImpl();
		weixinService.setAppId(appId);
		weixinService.setSecret(secret);
		weixinService.setProxy(PROXY);
		weixinService.init();
		return weixinService;
	}

}
