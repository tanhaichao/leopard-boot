package io.leopard.boot.onum.dynamic.service;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.NotImplementedException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.jdbc.Jdbc;
import io.leopard.jdbc.builder.InsertBuilder;
import io.leopard.jdbc.builder.NullInsertBuilder;
import io.leopard.jdbc.builder.NullUpdateBuilder;
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
	public List<DynamicEnumConstantEntity> list(String enumId) {
		String sql = "select * from " + TABLE_NAME + " where enumId=? order by position asc";
		List<DynamicEnumConstantEntity> constantList = jdbc.queryForList(sql, DynamicEnumConstantEntity.class, enumId);
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
	public boolean add(DynamicEnumConstantEntity entity, Operator operator) {
		InsertBuilder builder = new NullInsertBuilder(TABLE_NAME);
		builder.setString("enumId", entity.getEnumId());
		builder.setString("key", entity.getKey());
		builder.setString("desc", entity.getDesc());
		builder.setString("remark", entity.getRemark());
		builder.setInt("position", entity.getPosition());
		builder.setDate("posttime", entity.getPosttime());
		builder.setDate("lmodify", entity.getLmodify());
		return jdbc.insertForBoolean(builder);
		// return false;
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		String sql = "delete from " + TABLE_NAME + " where enumId=? and `key`=?";
		return jdbc.updateForBoolean(sql, enumId, key);
	}

	@Override
	public boolean update(DynamicEnumConstantEntity entity, Operator operator) {
		UpdateBuilder builder = new NullUpdateBuilder(TABLE_NAME);
		builder.setString("desc", entity.getDesc());
		builder.setInt("position", entity.getPosition());
		builder.setString("remark", entity.getRemark());
		builder.setDate("lmodify", entity.getLmodify());

		builder.where.setString("enumId", entity.getEnumId());
		builder.where.setString("key", entity.getKey());
		return jdbc.updateForBoolean(builder);
	}

	@Override
	public List<DynamicEnumConstantEntity> listAll() {
		String sql = "select * from " + TABLE_NAME + " order by enumId asc, position asc";
		return jdbc.queryForList(sql, DynamicEnumConstantEntity.class);
	}

	@Override
	public Date getLastModifyTime() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public boolean updateAll(List<DynamicEnumConstantEntity> allConstantList) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public DynamicEnumConstantEntity get(String enumId, String key) {
		String sql = "select * from " + TABLE_NAME + " where enumId=? and `key`=?";
		return jdbc.query(sql, DynamicEnumConstantEntity.class, enumId, key);
	}
}
