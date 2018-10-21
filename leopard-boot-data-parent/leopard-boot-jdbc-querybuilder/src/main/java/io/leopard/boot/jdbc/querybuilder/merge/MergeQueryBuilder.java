package io.leopard.boot.jdbc.querybuilder.merge;

import java.util.ArrayList;
import java.util.List;

import io.leopard.boot.jdbc.querybuilder.SearchBuilder;
import io.leopard.jdbc.StatementParameter;

/**
 * 多表合并查询
 * 
 * @author 谭海潮
 *
 */
public class MergeQueryBuilder extends SearchBuilder {

	private static final String UNION_TABLE_ALIAS = "a";// 表别名

	private List<String> columnNameList;

	public MergeQueryBuilder(String... columnNames) {
		this.tableAlias = UNION_TABLE_ALIAS + ".";
		columnNameList = new ArrayList<>();
		for (String columnName : columnNames) {
			columnNameList.add(columnName);
		}
	}

	/**
	 * 表查询列表
	 */
	private List<TableQuery> tableQueryList = new ArrayList<>();

	public TableQuery addTable(String tableName) {
		TableQuery table = new TableQuery(tableName, columnNameList);
		tableQueryList.add(table);
		return table;
	}

	@Override
	protected String generateSelectSQL(StatementParameter param) {
		StringBuilder union = new StringBuilder();
		union.append("select * from (");
		int index = 0;
		for (TableQuery tableQuery : tableQueryList) {
			if (index > 0) {
				union.append(" UNION ALL ");
			}
			union.append(tableQuery.toSql());
			index++;
		}
		union.append(") " + UNION_TABLE_ALIAS + " ");
		String searchSql = generateSearchSQL(param);
		return union.toString() + searchSql;
	}
}
