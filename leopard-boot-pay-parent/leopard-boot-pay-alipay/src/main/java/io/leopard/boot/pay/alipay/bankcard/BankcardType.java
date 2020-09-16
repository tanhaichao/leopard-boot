package io.leopard.boot.pay.alipay.bankcard;

import io.leopard.lang.inum.Snum;

/**
 * 银行卡类型
 * 
 * @author 谭海潮
 *
 */
public enum BankcardType implements Snum {

	DC("DC", "储蓄卡")//
	, CC("CC", "信用卡")//
	;

	private String key;

	private String desc;

	private BankcardType(String key, String desc) {
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
