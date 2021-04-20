package io.leopard.boot.jdbc.querybuilder.merge;

import org.junit.Test;

import io.leopard.jdbc.StatementParameter;

public class MergeQueryBuilderTest {

	@Test
	public void MergeQueryBuilder() {
		String contractNo = "aa";

		MergeQueryBuilder builder = new MergeQueryBuilder("contractId", "contractNo", "type", "posttime", "firstEnterpriseId", "secondEnterpriseId");
		builder = new MergeQueryBuilder(AllContractVO.class);
		builder.addTable("procurement_contract").literal("type", ContractType.PROCUREMENT); // 采购合同
		builder.addTable("transport_contract").literal("type", ContractType.TRANSPORT); // 运输合同
		builder.addTable("sales_customer_contract").literal("type", ContractType.SALES_CUSTOMER); // 融资客户合同(销售)
		builder.addLike("contractNo", contractNo);
		builder.order("posttime");

		StatementParameter param = new StatementParameter();
		String sql = builder.generateSelectSQL(param);
		System.out.println("sql:" + sql);

		// System.err.println("sql:" + sql);
		// return builder.queryForPage(jdbc, AllContractVO.class, start, size);
	}

}