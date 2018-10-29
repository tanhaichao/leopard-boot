package io.leopard.boot.jdbc.querybuilder.stat;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;

public class CounterBuilder {
	/**
	 * 搜索条件
	 */
	private StatSearchBuilder search;

	public CounterBuilder(StatSearchBuilder search) {
		this.search = search;
	}

	private StringBuilder select = new StringBuilder();

	public StringBuilder append(String str) {
		return select.append(str);
	}

	public int count(Jdbc jdbc) {
		StatementParameter param = new StatementParameter();
		String sql = select.toString() + search.generateWhereSQL(param);
		System.err.println("counter:" + sql);
		return jdbc.queryForInt(sql, param);
	}
}
