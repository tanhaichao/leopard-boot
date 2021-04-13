package io.leopard.boot.jdbc.querybuilder;

import io.leopard.jdbc.StatementParameter;

public class InQueryBuilder extends SearchBuilder {
	private String tableName;

	private String fieldName;

	public InQueryBuilder(String tableName, String fieldName) {
		this.tableName = tableName;
		this.fieldName = fieldName;
	}

	@Override
	protected String generateSelectSQL(StatementParameter param) {
		return "select " + fieldName + " from `" + tableName + "`" + generateSearchSQL(param);
	}

	/**
	 * 是否有where条件
	 * 
	 * @return
	 */
	public boolean hasWhere() {
		if (this.range != null) {
			return true;
		}
		if (!this.whereMap.isEmpty()) {
			return true;
		}
		if (!this.whereExpressionList.isEmpty()) {
			return true;
		}
		if (!this.likeMap.isEmpty()) {
			return true;
		}
		if (!this.inTableMap.isEmpty()) {
			return true;
		}
		return false;
	}
}
