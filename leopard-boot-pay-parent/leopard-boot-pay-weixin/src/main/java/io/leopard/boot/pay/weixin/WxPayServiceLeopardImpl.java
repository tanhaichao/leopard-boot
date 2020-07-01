package io.leopard.boot.pay.weixin;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.github.binarywang.wxpay.config.WxPayConfig;
import com.github.binarywang.wxpay.service.impl.WxPayServiceImpl;

/**
 * 微信支付
 * 
 * @author 谭海潮
 *
 */
@Component
@ConditionalOnProperty(prefix = "weixin", name = "appId")
public class WxPayServiceLeopardImpl extends WxPayServiceImpl {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${weixin.appId}")
	private String appId;

	@Value("${weixin.mchId}")
	private String mchId;

	@Value("${weixin.mchKey}")
	private String mchKey;

	@PostConstruct
	public void init() {
		WxPayConfig payConfig = new WxPayConfig();
		payConfig.setAppId(appId);
		payConfig.setMchId(mchId);
		payConfig.setMchKey(mchKey);
		this.setConfig(payConfig);
	}
}
