package io.leopard.boot.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

//@Service("leopardBootWxmpServiceImpl")
// @ConditionalOnProperty(prefix = "weixin.mp", name = "secret")
public class WxmpServiceImpl implements WxmpService {

	protected Log logger = LogFactory.getLog(this.getClass());

	// @Value("${weixin.appId}")
	private String appId;

	// @Value("${weixin.secret}")
	private String secret;

	// @Value("${leopard.proxy:}") // 默认为empty
	private String proxyConfig;// 格式 ip:port

	private Proxy proxy;

	private WeixinAccessTokenDao weixinAccessTokenDao;

	@Override
	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public WeixinAccessTokenDao getWeixinAccessTokenDao() {
		return weixinAccessTokenDao;
	}

	public void setWeixinAccessTokenDao(WeixinAccessTokenDao weixinAccessTokenDao) {
		this.weixinAccessTokenDao = weixinAccessTokenDao;
	}

	public String getProxyConfig() {
		return proxyConfig;
	}

	public void setProxyConfig(String proxyConfig) {
		this.proxyConfig = proxyConfig;
	}

	@PostConstruct
	public void init() {
		logger.info("WeixinService proxy:" + this.proxyConfig);

		if (!StringUtils.isEmpty(this.proxyConfig)) {
			proxy = Httpnb.newHttpProxy(this.proxyConfig);
		}
	}

	@Override
	public AccessToken getAccessToken() {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appId", appId);
		params.put("secret", secret);
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String json = Httpnb.doGet(url, proxy, params);
		// TODO 出错判断
		// {"errcode":40002,"errmsg":"invalid grant_type rid: 5f386c70-441d34a9-7baebd7c"}
		logger.info("getAccessToken:" + json);
		return Json.toObject(json, AccessToken.class);
	}

	@Override
	public InputStream getWxaQrcode(String path, int width) throws IOException {
		if (width <= 0) {
			width = 430;
		}
		if (width > 1280) {
			width = 1280;
		}
		AccessToken accessToken = this.getAccessToken();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("path", path);
		params.put("width", width);
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(params);
		// String json = Httpnb.doPost(url, proxy, null, body);
		InputStream input = Httpnb.doPostForInputStream(url, proxy, null, body);
		return input;
	}

	@Override
	public InputStream getUnlimitedWxaQrcode(String path, int width) throws IOException {
		// https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
		if (path.startsWith("/")) {
			throw new IllegalArgumentException("path不能以/开始");
		}
		if (width <= 0) {
			width = 430;
		}
		if (width > 1280) {
			width = 1280;
		}
		AccessToken accessToken = this.getAccessToken();

		Map<String, Object> params = new LinkedHashMap<>();
		// params.put("scene", scene);
		params.put("path", path);
		params.put("width", width);
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(params);
		// String json = Httpnb.doPost(url, proxy, null, body);
		InputStream input = Httpnb.doPostForInputStream(url, proxy, null, body);
		return input;
	}

	@Override
	public boolean refreshAccessToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean forceRefreshAccessToken() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public AccessToken getAccessTokenByHttp() {
		// TODO Auto-generated method stub
		return null;
	}
}
