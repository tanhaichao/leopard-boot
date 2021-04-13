package io.leopard.boot.jdbc.querybuilder;

import org.junit.Test;

import io.leopard.jdbc.StatementParameter;

public class QueryBuilderTest {

	@Test
	public void QueryBuilder() {

	}

	@Test
	public void generateSelectSQL() {
		QueryBuilder builder = new QueryBuilder("order");

		{
			InQueryBuilder in = builder.addIn("orderId", "order_product");
			in.addLong("productId", 1L);
		}
		StatementParameter param = new StatementParameter();
		String sql = builder.generateSelectSQL(param);
		System.out.println(sql);
	}

}