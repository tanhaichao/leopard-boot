package io.leopard.boot.jdbc.querybuilder.stat;

import io.leopard.boot.jdbc.querybuilder.SearchBuilder;
import io.leopard.jdbc.StatementParameter;

public class StatSearchBuilder extends SearchBuilder {

	@Override
	protected String generateSelectSQL(StatementParameter param) {
		return generateSearchSQL(param);
	}

	@Override
	protected String columnName(String fieldName) {
		if (fieldName.indexOf(".") != -1) {
			return fieldName;
		}
		return super.columnName(fieldName);
	}

	@Override
	protected String generateWhereSQL(StatementParameter param) {
		return super.generateWhereSQL(param);
	}

	@Override
	public SearchBuilder limit(int start, int size) {
		throw new UnsupportedOperationException("不支持使用limit.");
	}

}
