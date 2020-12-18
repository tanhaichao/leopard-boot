package io.leopard.boot.weixin;

import java.net.Proxy;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import io.leopard.boot.weixin.model.AccessToken;
import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

@Component
public class WeixinAccessTokenDaoHttpImpl implements WeixinAccessTokenDao {

	protected Log logger = LogFactory.getLog(this.getClass());

	@Override
	public AccessToken getAccessToken(String appId, String secret, Proxy proxy) {
		Map<String, Object> params = new LinkedHashMap<>();
		params.put("grant_type", "client_credential");
		params.put("appId", appId);
		params.put("secret", secret);
		String url = "https://api.weixin.qq.com/cgi-bin/token";
		String json = Httpnb.doGet(url, proxy, params);
		{
			Map<String, Object> obj = Json.toMap(json);
			Integer errCode = (Integer) obj.get("errcode");
			if (errCode != null) {
				logger.error("json:" + json);
				if (errCode == 40164) {
					throw new RuntimeException("当前IP无权访问微信接口。");
				}
				// json:{"errcode":40163,"errmsg":"code been used, hints: [ req_id: xxx ]"}
				// String errmsg = (String) obj.get("errmsg");
				throw new RuntimeException("微信接口请求失败，" + "errCode:" + errCode);
			}
		}
		// TODO 出错判断
		// {"errcode":40002,"errmsg":"invalid grant_type rid: 5f386c70-441d34a9-7baebd7c"}
		// at [Source: {"errcode":40164,"errmsg":"invalid ip 47.99.65.160 ipv6 ::ffff:47.99.65.160, not in whitelist rid: 5fd1befd-7eeb5dbe-"
		logger.info("getAccessToken:" + json);
		return Json.toObject(json, AccessToken.class);
	}

	@Override
	public boolean updateAccessToken(String appId, AccessToken accessToken) {
		throw new NotImplementedException("not impl.");
	}
}
