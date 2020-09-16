package io.leopard.boot.pay.alipay;

import java.net.Proxy;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.leopard.httpnb.Httpnb;

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
		return null;
	}
}
