package io.leopard.boot.jdbc.querybuilder.merge;

import io.leopard.lang.inum.Option;
import io.leopard.lang.inum.Snum;

/**
 * 合同类型
 * 
 * @author 谭海潮
 */
@Option
public enum ContractType implements Snum {
	BUY_AGREEMENT("buy_agreement", "代采协议"), SELL_AGREEMENT("sell_agreement", "代销协议"), PROCUREMENT("procurement", "采购合同"), SALES("sales", "销售合同"), TRANSPORT("transport",
			"运输合同"), PROCUREMENT_CUSTOMER("procurement_customer", "融资客户合同(采购)"), SALES_CUSTOMER("sales_customer", "融资客户合同(销售)"), STORAGE("storage", "仓储合同");

	private String key;

	private String desc;

	private ContractType(String key, String desc) {
		this.key = key;
		this.desc = desc;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}