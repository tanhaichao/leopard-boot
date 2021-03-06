package io.leopard.boot.weixin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import io.leopard.boot.util.AssertUtil;
import io.leopard.boot.weixin.form.TemplateMessageForm;
import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.boot.weixin.model.JSCode2Session;
import io.leopard.boot.weixin.model.OffiaccountUserinfo;
import io.leopard.boot.weixin.model.Qrcode;
import io.leopard.boot.weixin.model.WeixinMedia;
import io.leopard.boot.weixin.model.WeixinMobile;
import io.leopard.boot.weixin.model.WeixinUserinfo;
import io.leopard.boot.weixin.util.WeixinMediaUploadUtil;
import io.leopard.boot.weixin.util.WeixinUtil;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

public abstract class AbstractWeixinService implements WeixinService {

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

	public void setWeixinAccessTokenDao(WeixinAccessTokenDao weixinAccessTokenDao) {
		this.weixinAccessTokenDaoCacheImpl = weixinAccessTokenDao;
	}

	public abstract WeixinConfig createWeixinConfig();

	@PostConstruct
	public void init() {
		WeixinConfig weixinConfig = this.createWeixinConfig();
		this.appId = weixinConfig.getAppId();
		this.secret = weixinConfig.getSecret();

		logger.info("WeixinService proxy:" + weixinConfig.getProxy());
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
		return weixinAccessTokenDaoCacheImpl.getAccessToken(appId, secret, proxy);
		// Map<String, Object> params = new LinkedHashMap<>();
		// params.put("grant_type", "client_credential");
		// params.put("appId", appId);
		// params.put("secret", secret);
		// String url = "https://api.weixin.qq.com/cgi-bin/token";
		// String json = Httpnb.doGet(url, proxy, params);
		// {
		// Map<String, Object> obj = Json.toMap(json);
		// Integer errCode = (Integer) obj.get("errcode");
		// if (errCode != null) {
		// logger.error("json:" + json);
		// if (errCode == 40164) {
		// throw new RuntimeException("当前IP无权访问微信接口。");
		// }
		// // json:{"errcode":40163,"errmsg":"code been used, hints: [ req_id: xxx ]"}
		// // String errmsg = (String) obj.get("errmsg");
		// throw new RuntimeException("微信接口请求失败，" + "errCode:" + errCode);
		// }
		// }
		// // TODO 出错判断
		// // {"errcode":40002,"errmsg":"invalid grant_type rid: 5f386c70-441d34a9-7baebd7c"}
		// // at [Source: {"errcode":40164,"errmsg":"invalid ip 47.99.65.160 ipv6 ::ffff:47.99.65.160, not in whitelist rid: 5fd1befd-7eeb5dbe-"
		// logger.info("getAccessToken:" + json);
		// return Json.toObject(json, AccessToken.class);
	}

	@Override
	public Qrcode getQrcodeStrScene(String sceneStr, int expireSeconds) {
		if (StringUtils.isEmpty(sceneStr)) {
			throw new IllegalArgumentException("sceneStr不能为空.");
		}
		if (sceneStr.length() > 64) {
			throw new IllegalArgumentException("sceneStr不能超过64位.");
		}
		if (expireSeconds < 60) {
			expireSeconds = 60;
		}
		if (expireSeconds > 2592000) {
			expireSeconds = 2592000;
		}
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();

		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken.getAccess_token();

		String body = "{\"expire_seconds\": " + expireSeconds + ", \"action_name\": \"QR_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
		// params.put("body", body);

		// 2020-08-17 02:23:18.503 [main]leopard-test INFO io.leopard.boot.weixin.WeixinServiceImpl -
		// getQrcodeLimitStrScene:{"ticket":"gQHK8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLUhQYTRxc3ZjbDMxMDAwME0wN1AAAgQz6jhfAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/02-HPa4qsvcl310000M07P"}
		logger.info("getQrcodeStrScene sceneStr:" + sceneStr + " expireSeconds:" + expireSeconds);

		String json = Httpnb.doPost(url, proxy, params, body);
		logger.info("getQrcodeStrScene:" + json);

		// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET

		// https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN

		return Json.toObject(json, Qrcode.class);
	}

	@Override
	public Qrcode getWxaQrcode(String path, int width) {
		if (width <= 0) {
			width = 430;
		}
		if (width > 1280) {
			width = 1280;
		}
		Map<String, Object> params = new LinkedHashMap<>();
		AccessToken accessToken = this.getAccessToken();

		String url = "https://api.weixin.qq.com/cgi-bin/wxaapp/createwxaqrcode?access_token=" + accessToken.getAccess_token();

		String json = Httpnb.doPost(url, proxy, params);
		logger.info("getWxaQrcode:" + json);

		return null;
	}

	@Override
	public Qrcode getQrcodeLimitStrScene(String sceneStr) {
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();

		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=" + accessToken.getAccess_token();

		String body = "{\"action_name\": \"QR_LIMIT_STR_SCENE\", \"action_info\": {\"scene\": {\"scene_str\": \"" + sceneStr + "\"}}}";
		// params.put("body", body);

		// 2020-08-17 02:23:18.503 [main]leopard-test INFO io.leopard.boot.weixin.WeixinServiceImpl -
		// getQrcodeLimitStrScene:{"ticket":"gQHK8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyLUhQYTRxc3ZjbDMxMDAwME0wN1AAAgQz6jhfAwQAAAAA","url":"http:\/\/weixin.qq.com\/q\/02-HPa4qsvcl310000M07P"}

		String json = Httpnb.doPost(url, proxy, params, body);
		logger.info("getQrcodeLimitStrScene:" + json);

		this.checkResultStatus(json);
		if (false) {
			if (json.indexOf("\"errcode\"") != -1) {
				// {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest rid: 5fef0f85-3dc91dca-6182adeb"}
				logger.error("appId:" + appId + " accessToken:" + Json.toJson(accessToken));
				throw new RuntimeException("访问微信接口出错[" + this.appId + "].");
			}
		}
		// https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=TICKET

		// https://api.weixin.qq.com/cgi-bin/qrcode/create?access_token=TOKEN

		return Json.toObject(json, Qrcode.class);
	}

	protected void checkResultStatus(String json) {
		if (json.indexOf("\"errcode\"") != -1) {
			Map<String, Object> data = Json.toMap(json);
			int errcode = (int) data.get("errcode");
			if (errcode == 40001) {
				throw new InvalidCredentialException("微信AccessToken已过期[" + this.appId + "]。");
			}
			// {"errcode":40001,"errmsg":"invalid credential, access_token is invalid or not latest rid: 5fef0f85-3dc91dca-6182adeb"}
			// logger.error("appId:" + appId + " accessToken:" + Json.toJson(accessToken));
			throw new RuntimeException("访问微信接口出错[" + this.appId + "].");
		}
	}

	@Override
	public OffiaccountUserinfo getUserinfo(String openId) {
		// String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=ACCESS_TOKEN&openid=OPENID&lang=zh_CN";
		AccessToken accessToken = this.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/user/info?access_token=" + accessToken.getAccess_token();
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("openid", openId);
		params.put("lang", "zh_CN");
		// params.put("body", body);
		String json = Httpnb.doPost(url, proxy, params);
		logger.info("getUserinfo openId:" + openId + " json:" + json);
		if (json.indexOf("\"errcode\"") != -1) {
			// logger.error("getUserinfo:" + json);
			throw new RuntimeException("访问微信接口出错[" + openId + "].");
		}
		// TODO 错误判断
		return Json.toObject(json, OffiaccountUserinfo.class, true);
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
		logger.info("sendTemplateMessage:" + Json.toFormatJson(message));
		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(message);
		// params.put("body", body);
		String json = Httpnb.doPost(url, proxy, params, body);
		// {"errcode":40165,"errmsg":"invalid weapp pagepath rid: 5fac9bec-09551c12-23b1a813"}
		logger.info("sendTemplateMessage:" + json);

		Map<String, Object> result = Json.toMap(json);
		Integer errcode = (Integer) result.get("errcode");
		if (errcode != null && errcode != 0) {
			logger.error("sendTemplateMessage:" + json);
		}
	}

	@Override
	public WeixinMedia uploadImageMedia(File file) throws IOException {
		AccessToken accessToken = this.getAccessToken();

		// String accessToken = "40_Y-O_cz2JpQKKSuEfW9s-faH8--odGNu9HqqL3FWlXrMCxiVkTaPkjHku5DkjTcemeyMXm6Ylhnp-hlO36MMMaAEAUUU";
		String json = WeixinMediaUploadUtil.uploadImageMedia(accessToken.getAccess_token(), file);
		logger.info("json:" + json);
		{
			Map<String, Object> result = Json.toMap(json);
			Integer errcode = (Integer) result.get("errcode");
			if (errcode != null && errcode != 0) {
				throw new RuntimeException("上传素材出错。");
			}
		}
		return Json.toObject(json, WeixinMedia.class, true);
	}

	@Override
	public WeixinMedia uploadImageMedia(InputStream input, String fileName) throws IOException {
		AccessToken accessToken = this.getAccessToken();

		// String accessToken = "40_Y-O_cz2JpQKKSuEfW9s-faH8--odGNu9HqqL3FWlXrMCxiVkTaPkjHku5DkjTcemeyMXm6Ylhnp-hlO36MMMaAEAUUU";
		String json = WeixinMediaUploadUtil.uploadImageMedia(accessToken.getAccess_token(), fileName, input);
		logger.info("json:" + json);
		{
			Map<String, Object> result = Json.toMap(json);
			Integer errcode = (Integer) result.get("errcode");
			if (errcode != null && errcode != 0) {
				throw new RuntimeException("上传素材出错。");
			}
		}
		return Json.toObject(json, WeixinMedia.class, true);
	}

	@Override
	public void sendTextMessage(String openId, String content) {
		// {
		// "touser":"OPENID",
		// "msgtype":"text",
		// "text":
		// {
		// "content":"Hello World"
		// }
		// }
		Map<String, Object> message = new LinkedHashMap<>();
		message.put("content", content);
		this.sendCustomMessage(openId, "text", message);
	}

	@Override
	public void sendImageMessage(String openId, String mediaId) {
		// {
		// "touser":"OPENID",
		// "msgtype":"image",
		// "image":
		// {
		// "media_id":"MEDIA_ID"
		// }
		// }
		Map<String, Object> message = new LinkedHashMap<>();
		message.put("media_id", mediaId);
		this.sendCustomMessage(openId, "image", message);
	}

	protected void sendCustomMessage(String openId, String msgtype, Map<String, Object> message) {
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();
		Map<String, Object> messageParam = new LinkedHashMap<>();
		messageParam.put("touser", openId);
		messageParam.put("msgtype", msgtype);
		messageParam.put(msgtype, message);
		logger.info("sendTextMessage openId:" + openId + " msgtype:" + msgtype);
		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/message/custom/send?access_token=" + accessToken.getAccess_token();
		String body = Json.toJson(messageParam);
		// params.put("body", body);
		String json = Httpnb.doPost(url, proxy, params, body);
		// {"errcode":40165,"errmsg":"invalid weapp pagepath rid: 5fac9bec-09551c12-23b1a813"}
		logger.info("sendTextMessage:" + json);

		Map<String, Object> result = Json.toMap(json);
		Integer errcode = (Integer) result.get("errcode");
		if (errcode != null && errcode != 0) {
			logger.error("sendTextMessage:" + json);
		}
	}

	@Override
	public void createMenu(String body) {
		// https://developers.weixin.qq.com/doc/offiaccount/Custom_Menus/Creating_Custom-Defined_Menu.html
		AccessToken accessToken = this.getAccessToken();
		Map<String, Object> params = new LinkedHashMap<>();
		// {"action_name": "QR_LIMIT_STR_SCENE", "action_info": {"scene": {"scene_str": "test"}}}
		String url = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=" + accessToken.getAccess_token();
		// String body = Json.toJson(message);
		// params.put("body", body);
		String json = Httpnb.doPost(url, proxy, params, body);
		logger.info("createMenu:" + json);
	}

	@Override
	public void industry(String industryId1, String industryId2) {
		AssertUtil.assertNotEmpty(industryId1, "industryId1不能为空");
		AssertUtil.assertNotEmpty(industryId2, "industryId2不能为空");
		AccessToken accessToken = this.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/api_set_industry?access_token=" + accessToken.getAccess_token();
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("industry_id1", industryId1);
		params.put("industry_id2", industryId2);
		String body = Json.toJson(params);

		String json = Httpnb.doPost(url, proxy, null, body);
		logger.info("industry:" + json);

	}

	@Override
	public void getIndustry() {
		AccessToken accessToken = this.getAccessToken();
		String url = "https://api.weixin.qq.com/cgi-bin/template/get_industry?access_token=" + accessToken.getAccess_token();

		String json = Httpnb.doGet(url);
		logger.info("getIndustry:" + json);

	}

}
