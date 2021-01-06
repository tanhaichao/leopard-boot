package io.leopard.boot.weixin;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class WeixinServiceFactory {

	protected static Log logger = LogFactory.getLog(WeixinServiceFactory.class);

	// @Value("${leopard.proxy:}") // 默认为empty
	// private String proxy;// 格式 ip:port
	//
	// private static String PROXY;// 格式 ip:port
	//
	// @PostConstruct
	// public void init() {
	// logger.info("init:" + proxy);
	// PROXY = proxy;
	// }

	public static WeixinService create(String appId, String secret) {
		return create(appId, secret, null, new WeixinAccessTokenDaoCacheImpl());
	}

	public static AbstractWeixinService create(String appId, String secret, String proxy, WeixinAccessTokenDao weixinAccessTokenDao) {
		// if (weixinAccessTokenDao == null) {
		// weixinAccessTokenDao = new WeixinAccessTokenDaoHttpImpl();
		// }
		AbstractWeixinService weixinService = new AbstractWeixinService() {
			@Override
			public WeixinConfig createWeixinConfig() {
				WeixinConfig weixinConfig = new WeixinConfig();
				weixinConfig.setAppId(appId);
				weixinConfig.setProxy(proxy);
				weixinConfig.setSecret(secret);
				return weixinConfig;
			}

		};
		weixinService.init();
		return weixinService;
	}

}
