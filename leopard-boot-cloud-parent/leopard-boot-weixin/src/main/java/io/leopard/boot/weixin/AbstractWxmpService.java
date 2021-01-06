package io.leopard.boot.weixin;

import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
//import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import io.leopard.boot.util.AssertUtil;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

public abstract class AbstractWxmpService implements WxmpService {

	protected Log logger = LogFactory.getLog(this.getClass());
	@Autowired
	private WeixinAccessTokenDao weixinAccessTokenDaoCacheImpl;

	private String appId;

	private String secret;

	private Proxy proxy;

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

	public void setWeixinAccessTokenDao(WeixinAccessTokenDao weixinAccessTokenDao) {
		this.weixinAccessTokenDaoCacheImpl = weixinAccessTokenDao;
	}

	public abstract WeixinConfig createWeixinConfig();

	@PostConstruct
	public void init() {
		WeixinConfig weixinConfig = this.createWeixinConfig();
		this.appId = weixinConfig.getAppId();
		this.secret = weixinConfig.getSecret();
		logger.info("WxmpService proxy:" + weixinConfig.getProxy());
		if (!StringUtils.isEmpty(weixinConfig.getProxy())) {
			proxy = Httpnb.newHttpProxy(weixinConfig.getProxy());
		}
	}

	@Override
	public boolean refreshAccessToken() {
		return this.weixinAccessTokenDaoCacheImpl.refreshAccessToken(appId, secret, proxy);
	}

	@Override
	public boolean forceRefreshAccessToken() {
		return this.weixinAccessTokenDaoCacheImpl.forceRefreshAccessToken(appId, secret, proxy);
	}

	@Override
	public AccessToken getAccessTokenByHttp() {
		return this.weixinAccessTokenDaoCacheImpl.getAccessTokenByHttp(appId, secret, proxy);
	}

	@Override
	public AccessToken getAccessToken() {
		return weixinAccessTokenDaoCacheImpl.getAccessToken(appId, secret, proxy);
		// Map<String, Object> params = new LinkedHashMap<>();
		// params.put("grant_type", "client_credential");
		// params.put("appId", appId);
		// params.put("secret", secret);
		// String url = "https://api.weixin.qq.com/cgi-bin/token";
		// String json = Httpnb.doGet(url, proxy, params);
		// // TODO 出错判断
		// // {"errcode":40002,"errmsg":"invalid grant_type rid: 5f386c70-441d34a9-7baebd7c"}
		// logger.info("getAccessToken:" + json);
		// return Json.toObject(json, AccessToken.class);
	}

	@Override
	public InputStream getWxaQrcode(String path, int width) throws IOException {
		// if (width <= 0) {
		// width = 430;
		// }
		// if (width > 1280) {
		// width = 1280;
		// }
		// AccessToken accessToken = this.getAccessToken();
		//
		// Map<String, Object> params = new LinkedHashMap<>();
		// params.put("path", path);
		// params.put("width", width);
		// String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken.getAccess_token();
		// String body = Json.toJson(params);
		// // String json = Httpnb.doPost(url, proxy, null, body);
		// InputStream input = Httpnb.doPostForInputStream(url, proxy, null, body);
		// return input;
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
		// 获取小程序二维码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制，
		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(params);
		// String json = Httpnb.doPost(url, proxy, null, body);
		InputStream input = Httpnb.doPostForInputStream(url, proxy, null, body);
		return input;
	}

	public byte[] getWxaQrcodeData(String path, int width) throws IOException {
		InputStream input = this.getWxaQrcode(path, width); // "pages/index/main"
		byte[] data = IOUtils.toByteArray(input);
		input.reset();
		if (data.length < 500) {
			String json = new String(data);
			// {"errcode":45009,"errmsg":"reach max api daily quota limit rid: 5ff40105-180413a3-7d32d2a5"}
			Map<String, Object> obj = Json.toMap(json);
			Integer errCode = (Integer) obj.get("errcode");
			if (errCode != null) {
				logger.error("json:" + json);
				// json:{"errcode":40163,"errmsg":"code been used, hints: [ req_id: xxx ]"}
				if (errCode == 45009) {
					throw new RuntimeException("获取小程序码次数超过上限。");
				}
				if (errCode == 40001) {
					throw new InvalidCredentialException("微信AccessToken已过期[" + this.appId + "]。");
				}
				else {
					// {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest rid: 5fef0f85-3dc91dca-6182adeb"}
					// logger.error("appId:" + appId + " accessToken:" + Json.toJson(accessToken));
					throw new RuntimeException("访问微信接口出错[appId:" + this.appId + " errCode:" + errCode + "].");
				}
			}
		}
		return data;
	}

	@Override
	public InputStream getUnlimitedWxaQrcode(String page, String scene, int width) throws IOException {
		// https://developers.weixin.qq.com/miniprogram/dev/api-backend/open-api/qr-code/wxacode.getUnlimited.html
		AssertUtil.assertNotEmpty(page, "page参数不能为空.");
		AssertUtil.assertNotEmpty(scene, "scene参数不能为空.");
		if (page.startsWith("/")) {
			throw new IllegalArgumentException("page不能以/开始");
		}
		if (width <= 0) {
			width = 430;
		}
		if (width > 1280) {
			width = 1280;
		}
		AccessToken accessToken = this.getAccessToken();

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("scene", scene);
		params.put("page", page);
		params.put("width", width);
		// 获取小程序二维码，适用于需要的码数量较少的业务场景。通过该接口生成的小程序码，永久有效，有数量限制，
		// https://api.weixin.qq.com/wxa/getwxacodeunlimit
		String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(params);
		// String json = Httpnb.doPost(url, proxy, null, body);
		InputStream input = Httpnb.doPostForInputStream(url, proxy, null, body);
		return input;
	}

	public byte[] getUnlimitedWxaQrcodeData(String page, String scene, int width) throws IOException {
		AssertUtil.assertNotEmpty(page, "page参数不能为空.");
		AssertUtil.assertNotEmpty(scene, "scene参数不能为空.");
		InputStream input = this.getUnlimitedWxaQrcode(page, scene, width); // "pages/index/main"
		byte[] data = IOUtils.toByteArray(input);
		input.reset();

		if (data.length < 500) {
			String json = new String(data);
			// {"errcode":45009,"errmsg":"reach max api daily quota limit rid: 5ff40105-180413a3-7d32d2a5"}
			Map<String, Object> obj = Json.toMap(json);
			Integer errCode = (Integer) obj.get("errcode");
			if (errCode != null) {
				logger.error("json:" + json);
				// json:{"errcode":40163,"errmsg":"code been used, hints: [ req_id: xxx ]"}
				if (errCode == 45009) {
					throw new RuntimeException("获取小程序码次数超过上限。");
				}
				if (errCode == 40001) {
					throw new InvalidCredentialException("微信AccessToken已过期[" + this.appId + "]。");
				}
				else {
					// {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest rid: 5fef0f85-3dc91dca-6182adeb"}
					// logger.error("appId:" + appId + " accessToken:" + Json.toJson(accessToken));
					throw new RuntimeException("访问微信接口出错[appId:" + this.appId + " errCode:" + errCode + "].");
				}
			}
		}
		return data;
	}
}
