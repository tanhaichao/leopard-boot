package io.leopard.boot.weixin;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import io.leopard.boot.weixin.form.TemplateMessageForm;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.Userinfo;
import io.leopard.boot.weixin.model.WeixinMobile;
import io.leopard.boot.weixin.model.WeixinUserinfo;
import io.leopard.boot.weixin.util.WeixinUtil;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

@Service("leopardBootWeixinServiceImpl")
@ConditionalOnProperty(prefix = "weixin", name = "secret")
public class WeixinServiceImpl implements WeixinService {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.secret}")
	private String secret;

	@Value("${leopard.proxy:}") // 默认为empty
	private String proxyConfig;// 格式 ip:port

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

	/**
	 * 获取微信小程序 用户信息
	 * 
	 * @param code 调用微信登陆返回的Code
	 * @return
	 */
	@Override
	public String getSessionKey(String code) {
		JSCode2Session session = this.jscode2Session(code);
		return session.getSessionKey();
	}

	@Override
	public JSCode2Session jscode2Session(String code) {
		logger.info("code:" + code);
		// System.out.println("iv:" + iv);
		// System.out.println("encryptedData:" + encryptedData);

		Map<String, Object> params = new LinkedHashMap<>();
		params.put("appId", appId);
		params.put("secret", secret);
		params.put("js_code", code);
		params.put("grant_type", "authorization_code");
		String json = Httpnb.doPost("https://api.weixin.qq.com/sns/jscode2session", proxy, params);
		logger.info("json:" + json);
		Map<String, Object> obj = Json.toMap(json);
		Integer errCode = (Integer) obj.get("errcode");
		if (errCode != null) {
			logger.error("json:" + json);
			// json:{"errcode":40163,"errmsg":"code been used, hints: [ req_id: xxx ]"}

			// String errmsg = (String) obj.get("errmsg");
			throw new RuntimeException("微信接口请求失败，" + "errCode:" + errCode);
		}
		String sessionKey = (String) obj.get("session_key");
		String openid = (String) obj.get("openid");
		String unionId = (String) obj.get("unionid");

		if (StringUtils.isEmpty(sessionKey)) {
			throw new RuntimeException("sessionKey怎么会为空?");
		}
		if (StringUtils.isEmpty(openid)) {
			throw new RuntimeException("openid怎么会为空?");
		}
		JSCode2Session session = new JSCode2Session();
		session.setOpenid(openid);
		session.setUnionId(unionId);
		session.setSessionKey(sessionKey);
		return session;
	}

	@Override
	public String getUnionIdByUserinfo(String sessionKey, String encryptedData, String iv) {
		WeixinUserinfo weixinUserinfo = (WeixinUserinfo) WeixinUtil.getData(sessionKey, encryptedData, iv);
		return weixinUserinfo.getUnionId();
	}

	@Override
	public WeixinMobile getWeixinMobile(String sessionKey, String encryptedData, String iv) {
		WeixinMobile weixinMobile = (WeixinMobile) WeixinUtil.getData(sessionKey, encryptedData, iv);
		return weixinMobile;
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
	public void getQrcodeLimitStrScene(String sceneStr) {
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();

		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken.getAccess_token();

		String body = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
		// params.put("body", body);

		String json = Httpnb.doPost(url, proxy, params, body);
		logger.info("getQrcodeLimitStrScene:" + json);

		// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET

		// https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN
	}

	public Userinfo getUserinfo() {

		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";

		return null;
	}

	@Override
	public void getAllPrivateTemplate() {
		AccessToken accessToken = this.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_all_private_template?access_token=" + accessToken.getAccess_token();
		String json = Httpnb.doGet(url);
		logger.info("json:" + json);
	}

	@Override
	public void sendTemplateMessage(TemplateMessageForm message) {
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();
		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(message);
		// params.put("body", body);
		String json = Httpnb.doPost(url, proxy, params, body);
		logger.info("getQrcodeLimitStrScene:" + json);
	}
}
