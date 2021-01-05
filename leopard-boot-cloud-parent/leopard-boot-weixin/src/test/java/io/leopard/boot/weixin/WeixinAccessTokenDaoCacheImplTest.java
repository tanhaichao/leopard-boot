package io.leopard.boot.weixin;

import java.util.Date;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class WeixinAccessTokenDaoCacheImplTest {

	@Autowired
	private WeixinAccessTokenDaoCacheImpl weixinAccessTokenDaoCacheImpl;

	@Test
	public void WeixinAccessTokenDaoCacheImpl() {
		long time = 1609848336259L;
		// time = 1609848336158L;
		Date date = new Date(time);
		System.out.println("date:" + date);
	}

}