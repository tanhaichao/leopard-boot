package io.leopard.boot.jdbc.querybuilder.merge;

import java.util.LinkedHashMap;
import java.util.List;
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

	List<String> columnNameList;

	public TableQuery(String tableName, List<String> columnNameList) {
		this.tableName = tableName;
		this.columnNameList = columnNameList;
	}

	/**
	 * 列信息<别名,原始名称或值>
	 */
	private Map<String, String> columnMap = new LinkedHashMap<>();

	public TableQuery alias(String columnName, String alias) {
		if (StringUtils.isEmpty(alias)) {
			throw new IllegalArgumentException("alias不能为空.");
		}
		if (StringUtils.isEmpty(columnName)) {
			throw new IllegalArgumentException("columnName不能为空.");
		}
		columnMap.put(columnName, "`" + alias + "`");
		return this;
	}

	public TableQuery literal(String columnName, String str) {
		columnMap.put(columnName, str);
		return this;
	}

	/**
	 * 设置字面量
	 * 
	 * @param alias
	 * @param snum
	 * @return
	 */
	public TableQuery literal(String columnName, Snum snum) {
		if (StringUtils.isEmpty(columnName)) {
			throw new IllegalArgumentException("列名不能为空.");
		}
		columnMap.put(columnName, "'" + snum.getKey() + "'");
		return this;
	}

	public String toSql() {

		StringBuilder sb = new StringBuilder();
		sb.append("select ");
		int index = 0;
		for (String columnName : columnNameList) {
			String value = columnMap.get(columnName);
			// System.err.println("columnName:" + columnName + " value:" + value);
			if (index > 0) {
				sb.append(", ");
			}
			if (value != null) {
				sb.append(value).append(" as ");
			}
			sb.append("`" + columnName + "`");
			index++;
		}
		sb.append(" from `" + tableName + "`");
		// "select contractId, '" + ContractType.PROCUREMENT.getKey() + "' type, contractNo,posttime from procurement_contract"
		return sb.toString();
	}

}
