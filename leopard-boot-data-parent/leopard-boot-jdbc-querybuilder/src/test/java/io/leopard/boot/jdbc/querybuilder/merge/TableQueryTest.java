package io.leopard.boot.jdbc.querybuilder.merge;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TableQueryTest {

	@Test
	public void TableQuery() {
		List<String> columnNameList = new ArrayList<>();
		columnNameList.add("contractId");
		columnNameList.add("posttime");
		columnNameList.add("lmodify");
		TableQuery query = new TableQuery("contract", columnNameList);
		query.alias("posttime", "createTime");
		query.literal("lmodify", "now()");

		String sql = query.toSql();
		System.out.println("sql:" + sql);
	}

}