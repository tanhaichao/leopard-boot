package io.leopard.jdbc.builder.merge;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.StringUtils;

import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.StatementParameter;
import io.leopard.jdbc.builder.QueryBuilder;
import io.leopard.jdbc.builder.QueryBuilder.SQLInfo;
import io.leopard.lang.Page;
import io.leopard.lang.inum.Snum;

/**
 * 多表合并查询
 * 
 * @author 谭海潮
 *
 */
public class MergeQueryBuilder {
	private String unionTableAlias = "a";// 表别名

	private Integer limitStart;

	private Integer limitSize;

	private String orderFieldName;

	// 按desc 还是asc
	private String orderDirection;

	/**
	 * 表查询列表
	 */
	private List<TableQuery> tableQueryList = new ArrayList<>();

	public TableQuery addTable(String tableName) {
		TableQuery table = new TableQuery(tableName);
		tableQueryList.add(table);
		return table;
	}

	public MergeQueryBuilder limit(int start, int size) {
		this.limitStart = start;
		this.limitSize = size;
		return this;
	}

	public static class SQLInfo {
		StatementParameter param;

		private String sql;

		public StatementParameter getParam() {
			return param;
		}

		public void setParam(StatementParameter param) {
			this.param = param;
		}

		public String getSql() {
			return sql;
		}

		public void setSql(String sql) {
			this.sql = sql;
		}

	}

	protected SQLInfo toSqlInfo() {
		if (tableQueryList.isEmpty()) {
			throw new IllegalArgumentException("还没有设置表信息.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select * from (");
		int index = 0;
		for (TableQuery tableQuery : tableQueryList) {
			if (index > 0) {
				sb.append(" UNION ALL ");
			}
			String sql = tableQuery.toSql();
			sb.append(sql);
			index++;
		}
		sb.append(") ").append(unionTableAlias);

		// if (StringUtils.isNotEmpty(contractNo)) {
		// sql += " where a.contractNo like '%" + StringUtil.escapeSQLParam(contractNo) + "%'";
		// }
		// sql += " order by a.posttime desc limit ?, ?;";

		if (orderFieldName != null && orderFieldName.length() > 0) {
			sb.append(" order by " + this.unionTableAlias + "." + orderFieldName + " " + orderDirection);
		}
		sb.append(" limit ?,?");

		StatementParameter param = new StatementParameter();
		param.setInt(limitStart);
		param.setInt(limitSize);
		String sql = sb.toString();
		SQLInfo sqlInfo = new SQLInfo();
		sqlInfo.setParam(param);
		sqlInfo.setSql(sql);
		return sqlInfo;

	}

	public MergeQueryBuilder order(String fieldName) {
		return this.order(fieldName, "desc");
	}

	public MergeQueryBuilder order(String fieldName, String orderDirection) {
		this.orderFieldName = fieldName;
		this.orderDirection = orderDirection;
		return this;
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType) {
		SQLInfo sqlInfo = this.toSqlInfo();
		// System.err.println("sql:" + sqlInfo.getSql());
		return jdbc.queryForPage(sqlInfo.getSql(), elementType, sqlInfo.getParam());
	}

	public <T> Page<T> queryForPage(Jdbc jdbc, Class<T> elementType, int start, int size) {
		this.limit(start, size);
		return this.queryForPage(jdbc, elementType);
	}
}
