package io.leopard.boot.weixin;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

@Service("leopardBootWeixinServiceImpl")
public class WeixinServiceImpl implements WeixinService {

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.secret}")
	private String secret;

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

	/**
	 * 获取微信小程序 用户信息
	 * 
	 * @param code 调用微信登陆返回的Code
	 * @return
	 */
	@Override
	public String getSessionKey(String code, String iv, String encryptedData) {
		JSCode2Session session = this.jscode2Session(code, iv, encryptedData);
		return session.getSessionKey();
	}

	@Override
	public JSCode2Session jscode2Session(String code, String iv, String encryptedData) {
		System.out.println("code:" + code);
		System.out.println("iv:" + iv);
		System.out.println("encryptedData:" + encryptedData);

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		String json = Httpnb.doPost("https://api.weixin.qq.com/sns/jscode2session", params);
		Map<String, Object> obj = Json.toMap(json);
		Integer errCode = (Integer) obj.get("errcode");
		if (errCode != null) {
			System.err.println("json:" + json);
			// String errmsg = (String) obj.get("errmsg");
			throw new RuntimeException("微信接口请求失败，" + "errCode:" + errCode);
		}
		String sessionKey = (String) obj.get("session_key");
		String openid = (String) obj.get("openid");

		if (StringUtils.isEmpty(sessionKey)) {
			throw new RuntimeException("sessionKey怎么会为空?");
		}
		if (StringUtils.isEmpty(openid)) {
			throw new RuntimeException("openid怎么会为空?");
		}
		System.out.println("json:" + json);
		JSCode2Session session = new JSCode2Session();
		session.setOpenid(openid);
		session.setSessionKey(sessionKey);
		return session;
	}
}
