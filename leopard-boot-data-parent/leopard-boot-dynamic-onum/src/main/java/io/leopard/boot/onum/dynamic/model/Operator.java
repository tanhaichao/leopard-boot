package io.leopard.boot.onum.dynamic.model;

/**
 * 操作人
 * 
 * @author 谭海潮
 *
 */
public class Operator {

	/**
	 * 数据库列名
	 */
	private String columnName;

	/**
	 * 账号ID
	 */
	private long accountId;

	public String getColumnName() {
		return columnName;
	}

	public long getAccountId() {
		return accountId;
	}

	/**
	 * 设置操作人
	 * 
	 * @param columnName
	 * @param accountId
	 */
	public void setOperator(String columnName, long accountId) {
		this.columnName = columnName;
		this.accountId = accountId;
	}
}
