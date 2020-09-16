package io.leopard.boot.pay.alipay;

import java.net.Proxy;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.leopard.httpnb.Httpnb;
import io.leopard.json.Json;

@Component
public class BankcardClient {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${leopard.proxy:}") // 默认为empty
	private String proxyConfig;// 格式 ip:port

	private Proxy proxy;

	@PostConstruct
	public void init() {
		logger.info("BankcardClient proxy:" + this.proxyConfig);
		if (!StringUtils.isEmpty(this.proxyConfig)) {
			proxy = Httpnb.newHttpProxy(this.proxyConfig);
		}
	}

	/**
	 * 银行卡验证
	 * 
	 * @param cardNo 卡号
	 * @return
	 */
	public BankcardInfo validateAndCacheCardInfo(String cardNo) {
		// TODO 卡号合法性验证
		String url = "https://ccdcapi.alipay.com/validateAndCacheCardInfo.json?cardNo=" + cardNo + "&cardBinCheck=true";
		String json = Httpnb.doGet(url, proxy, -1);
		// System.err.println(json);

		Map<String, Object> map = Json.toMap(json);
		List<?> messages = (List<?>) map.get("messages");
		if (!CollectionUtils.isEmpty(messages)) {
			@SuppressWarnings("unchecked")
			Map<String, Object> error = (Map<String, Object>) messages.get(0);
			String errorCode = (String) error.get("errorCodes");
			if ("CARD_BIN_NOT_MATCH".equals(errorCode)) {
				throw new RuntimeException("卡号不正确[" + cardNo + "].");
			}
			logger.warn(json);
			// System.err.println("error:" + error.getClass().getName() + " error:" + error);
			throw new RuntimeException("识别银行卡出错.");
		}
		boolean validated = (boolean) map.get("validated");
		if (!validated) {
			throw new RuntimeException("卡号无法验证[" + cardNo + "].");
		}
		System.out.println("messages:" + messages);
		return Json.toObject(json, BankcardInfo.class, true);
	}
}
