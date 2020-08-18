package io.leopard.boot.weixin.model;

import org.junit.Test;

import io.leopard.json.Json;

public class OffiaccountUserinfoTest {

	@Test
	public void setSubscribeSecondTime() {
		OffiaccountUserinfo userinfo = new OffiaccountUserinfo();
		userinfo.setSubscribeSecondTime(1597724995);
		Json.printFormat(userinfo, "userinfo");
	}

}