package io.leopard.boot.jdbc.querybuilder;

import io.leopard.jdbc.StatementParameter;

public class RelatedQueryBuilder extends SearchBuilder {

	private String subSelectSql;

	public RelatedQueryBuilder(String subSelectSql) {
		this.subSelectSql = subSelectSql;
		this.tableAlias = "aa";
	}

	@Override
	protected String generateSelectSQL(StatementParameter param) {
		String selectSql = "select * from (" + subSelectSql + ") aa";
		String searchSql = generateSearchSQL(param);
		// System.err.println("searchSql:"+searchSql);
		return selectSql + searchSql;
	}

}
