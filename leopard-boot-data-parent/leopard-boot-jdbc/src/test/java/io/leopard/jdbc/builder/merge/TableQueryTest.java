package io.leopard.jdbc.builder.merge;

import org.junit.Test;

public class TableQueryTest {

	@Test
	public void TableQuery() {
		TableQuery query = new TableQuery("contract");
		query.column("contractId");
		query.column("posttime", "createTime");

		String sql = query.toSql();
		System.out.println("sql:" + sql);
	}

}