package io.leopard.boot.onum.dynamic.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.UpdateBuilder;

@Repository
public class DynamicEnumDaoJdbcImpl implements DynamicEnumDao {
	/**
	 * 表名
	 */
	private static final String TABLE_NAME = "dynamic_enum";

	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	private Jdbc jdbc;

	// List<DynamicEnumRecord> list;
	//
	// public DynamicEnumDaoJdbcImpl() {
	// loadData();
	// }
	//
	// @Override
	// public void loadData() {
	// String sql = "select * from " + TABLE_NAME;
	// List<DynamicEnumRecord> list;
	// try {
	// list = jdbc.queryForList(sql, DynamicEnumRecord.class);
	// }
	// catch (BadSqlGrammarException e) {
	// return;
	// }
	// logger.info("loadData list:" + list);
	// this.list = list;
	// }

	@Override
	public List<DynamicEnumEntity> list(String enumId) {
		String sql = "select * from " + TABLE_NAME + " where enumId=? order by position asc";
		List<DynamicEnumEntity> constantList = jdbc.queryForList(sql, DynamicEnumEntity.class, enumId);
		return constantList;

		// List<DynamicEnumRecord> constantList = list.stream().filter(e -> enumId.equals(e.getEnumId()))//
		// .sorted((x, y) -> { // 按显示位置排序
		// return x.getPosition() - y.getPosition();
		//
		// })//
		// .collect(Collectors.toList());
		// return constantList;
	}

	@Override
	public boolean add(DynamicEnumEntity record, Operator operator) {
		InsertBuilder builder = new InsertBuilder(TABLE_NAME);
		builder.setString("enumId", record.getEnumId());
		builder.setString("key", record.getKey());
		builder.setString("desc", record.getDesc());
		builder.setInt("position", record.getPosition());
		return jdbc.insertForBoolean(builder);
		// return false;
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		String sql = "delete from " + TABLE_NAME + " where enumId=? and `key`=?";
		return jdbc.updateForBoolean(sql, enumId, key);
	}

	@Override
	public boolean update(DynamicEnumEntity record, Operator operator) {
		UpdateBuilder builder = new UpdateBuilder(TABLE_NAME);
		builder.setString("desc", record.getDesc());
		builder.setInt("position", record.getPosition());

		builder.where.setString("enumId", record.getEnumId());
		builder.where.setString("key", record.getKey());
		return jdbc.updateForBoolean(builder);
	}

	@Override
	public List<DynamicEnumEntity> listAll() {
		String sql = "select * from " + TABLE_NAME + " order by enumId asc, position asc";
		return jdbc.queryForList(sql, DynamicEnumEntity.class);
	}

	@Override
	public Date getLastModifyTime() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public boolean updateAll(List<DynamicEnumEntity> allConstantList) {
		throw new NotImplementedException("not impl.");
	}
}
