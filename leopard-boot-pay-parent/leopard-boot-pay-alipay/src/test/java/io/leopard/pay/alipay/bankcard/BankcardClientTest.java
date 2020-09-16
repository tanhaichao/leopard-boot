package io.leopard.pay.alipay.bankcard;

import org.junit.Test;

import io.leopard.boot.pay.alipay.bankcard.BankcardClient;
import io.leopard.boot.pay.alipay.bankcard.BankcardInfo;
import io.leopard.json.Json;

public class BankcardClientTest {

	private BankcardClient bankcardClient = new BankcardClient();

	@Test
	public void validateAndCacheCardInfo() {
		BankcardInfo bankcardInfo = bankcardClient.validateAndCacheCardInfo("6222005865412565805");
		// BankcardInfo bankcardInfo = bankcardClient.validateAndCacheCardInfo("622200586541256");
		Json.print(bankcardInfo, "bankcardInfo");
	}

}