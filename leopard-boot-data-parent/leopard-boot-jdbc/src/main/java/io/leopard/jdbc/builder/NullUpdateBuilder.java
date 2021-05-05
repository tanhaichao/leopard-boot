package io.leopard.jdbc.builder;

import java.util.Date;

import io.leopard.lang.inum.Onum;

public class NullUpdateBuilder extends UpdateBuilder {

	public NullUpdateBuilder(String tableName) {
		super(tableName);
		this.statementParameter.setAllowNull(true);
	}

	@Override
	public void setInt(String fieldName, Integer value) {
		// if (value != null) {
		// super.setInt(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setInt(value);
	}

	@Override
	public void setLong(String fieldName, Long value) {
		// if (value != null) {
		// super.setLong(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setLong(value);
	}

	@Override
	public void setFloat(String fieldName, Float value) {
		// if (value != null) {
		// super.setFloat(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setFloat(value);
	}

	@Override
	public void setDouble(String fieldName, Double value) {
		// if (value != null) {
		// super.setDouble(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setDouble(value);
	}

	@Override
	public void setDate(String fieldName, Date value) {
		// if (value != null) {
		// super.setDate(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setDate(value);
	}

	@Override
	public void setString(String fieldName, String value) {
		// if (value != null) {
		// super.setString(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setString(value);
	}

	@Override
	public void setEnum(String fieldName, Onum<?, ?> value) {
		// if (value != null) {
		// super.setEnum(fieldName, value);
		// }
		fieldList.add(fieldName);
		statementParameter.setEnum(value);
	}
}
