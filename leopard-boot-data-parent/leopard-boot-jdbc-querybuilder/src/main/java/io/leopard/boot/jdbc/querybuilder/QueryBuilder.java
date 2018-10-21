package io.leopard.boot.jdbc.querybuilder;

import java.util.List;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.lang.Page;
import io.leopard.lang.Paging;

public class QueryBuilder extends SearchBuilder {

	private String tableName;

	public QueryBuilder(String tableName) {
		this.tableName = tableName;
	}

	@Override
	protected String generateSelectSQL(StatementParameter param) {
		return "select * from `" + tableName + "`" + generateSearchSQL(param);
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = this.generateSelectSQL(param);
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPaging(sql, elementType, param);
	}

	public <T> Paging<T> queryForPaging(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPaging(jdbc, elementType);
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = this.generateSelectSQL(param);
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPage(sql, elementType, param);
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPage(jdbc, elementType);
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType) {
		StatementParameter param = new StatementParameter();
		String sql = this.generateSelectSQL(param);
		return jdbc.queryForList(sql, elementType, param);
	}

	public <T> List<T> queryForList(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForList(jdbc, elementType);
	}

	public int count(Jdbc jdbc) {
		StatementParameter param = new StatementParameter();
		String sql = "select count(*) from `" + this.tableName + "` " + this.generateWhereSQL(param);
		return jdbc.queryForInt(sql, param);
	}

	public Double sumForDouble(Jdbc jdbc, String columnName) {
		StatementParameter param = new StatementParameter();
		String sql = "select sum(`" + columnName + "`) from `" + this.tableName + "` " + this.generateWhereSQL(param);
		return jdbc.queryForDouble(sql, param);
	}

}
