package io.leopard.boot.pay.alipay.bankcard;

import io.leopard.lang.inum.Snum;

/**
 * 银行卡状态
 * 
 * @author 谭海潮
 *
 */
public enum BankcardStatus implements Snum {

	OK("ok", "正常")//
	, NO("no", "过期")//
	;

	private String key;

	private String desc;

	private BankcardStatus(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	public String getDesc() {
		return this.desc;
	}

}
