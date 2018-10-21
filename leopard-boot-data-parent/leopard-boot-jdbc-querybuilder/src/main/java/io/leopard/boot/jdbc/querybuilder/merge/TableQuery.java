package io.leopard.boot.jdbc.querybuilder.merge;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.util.StringUtils;

import io.leopard.lang.inum.Snum;

/**
 * 表查询
 * 
 * @author 谭海潮
 *
 */
public class TableQuery {
	/**
	 * 表名
	 */
	private String tableName;

	public TableQuery(String tableName) {
		this.tableName = tableName;
	}

	/**
	 * 列信息<别名,原始名称或值>
	 */
	private Map<String, String> columnMap = new LinkedHashMap<>();

	public TableQuery column(String alias) {
		if (StringUtils.isEmpty(alias)) {
			throw new IllegalArgumentException("alias不能为空.");
		}
		columnMap.put(alias, null);
		return this;
	}

	public TableQuery columns(String... columnNames) {
		for (String columnName : columnNames) {
			this.column(columnName);
		}
		return this;
	}

	public TableQuery column(String alias, String columnName) {
		if (StringUtils.isEmpty(alias)) {
			throw new IllegalArgumentException("alias不能为空.");
		}
		if (StringUtils.isEmpty(columnName)) {
			throw new IllegalArgumentException("columnName不能为空.");
		}
		columnMap.put(alias, "`" + columnName + "`");
		return this;
	}

	public TableQuery column(String alias, Snum snum) {
		if (StringUtils.isEmpty(alias)) {
			throw new IllegalArgumentException("alias不能为空.");
		}
		columnMap.put(alias, "'" + snum.getKey() + "'");
		return this;
	}

	public String toSql() {
		if (columnMap.isEmpty()) {
			throw new IllegalArgumentException("列名不允许为空.");
		}
		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		int index = 0;
		for (Entry<String, String> entry : columnMap.entrySet()) {
			String alias = entry.getKey();
			String value = entry.getValue();
			if (index > 0) {
				sb.append(", ");
			}
			if (value != null) {
				sb.append(value).append(" as ");
			}
			sb.append("`" + alias + "`");
			index++;
		}
		sb.append(" from `" + tableName + "`");
		// "select contractId, '" + ContractType.PROCUREMENT.getKey() + "' type, contractNo,posttime from procurement_contract"
		return sb.toString();
	}

}
