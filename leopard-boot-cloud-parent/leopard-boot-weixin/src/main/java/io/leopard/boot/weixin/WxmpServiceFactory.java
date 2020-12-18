// package io.leopard.boot.weixin;
//
// import javax.annotation.PostConstruct;
//
// import org.apache.commons.logging.Log;
// import org.apache.commons.logging.LogFactory;
// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.core.Ordered;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;
//
// @Component
// @Order(Ordered.HIGHEST_PRECEDENCE)
// public class WxmpServiceFactory {
//
// protected static Log logger = LogFactory.getLog(WxmpServiceFactory.class);
//
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
//
// public static WxmpService create(String appId, String secret) {
// if (PROXY == null) {
// throw new RuntimeException("WxmpService未初始化");
// }
// logger.info("PROXY:" + PROXY);
// return create(appId, secret, PROXY, null);
// }
//
// public static WxmpService create(String appId, String secret, String proxy, WeixinAccessTokenDao weixinAccessTokenDao) {
// if (weixinAccessTokenDao == null) {
// weixinAccessTokenDao = new WeixinAccessTokenDaoHttpImpl();
// }
// WxmpServiceImpl weixinService = new WxmpServiceImpl();
// weixinService.setAppId(appId);
// weixinService.setSecret(secret);
// weixinService.setProxyConfig(proxy);
// weixinService.setWeixinAccessTokenDao(weixinAccessTokenDao);
// weixinService.init();
// return weixinService;
// }
//
// }
