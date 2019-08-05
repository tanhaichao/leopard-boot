package io.leopard.boot.onum.dynamic.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.stereotype.Repository;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;

@Repository
@Primary
public class DynamicEnumDaoCacheImpl implements DynamicEnumDao {
	@Autowired
	private DynamicEnumDao dynamicEnumDaoMemoryImpl;

	@Autowired
	private DynamicEnumDao dynamicEnumDaoJdbcImpl;

	/**
	 * 最后修改时间
	 */
	private Date lastModifyTime = new Date();

	@PostConstruct
	public void init() {
		List<DynamicEnumConstantEntity> allConstantList;
		try {
			allConstantList = dynamicEnumDaoJdbcImpl.listAll();
		}
		catch (BadSqlGrammarException e) {
			String message = e.getMessage();
			if (message.endsWith("dynamic_enum' doesn't exist")) {// 兼容初始化时表不存在
				return;
			}
			else {
				throw e;
			}
		}
		dynamicEnumDaoMemoryImpl.updateAll(allConstantList);
	}

	@Override
	public List<DynamicEnumConstantEntity> list(String enumId) {
		List<DynamicEnumConstantEntity> constantList = dynamicEnumDaoMemoryImpl.list(enumId);
		if (constantList != null) {
			Collections.sort(constantList, new DynamicEnumRecordComparator());
		}
		return constantList;
	}

	@Override
	public List<DynamicEnumConstantEntity> listAll() {
		return dynamicEnumDaoJdbcImpl.listAll();
	}

	@Override
	public boolean add(DynamicEnumConstantEntity record, Operator operator) {
		this.dynamicEnumDaoJdbcImpl.add(record, operator);
		return dynamicEnumDaoMemoryImpl.add(record, operator);
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		dynamicEnumDaoJdbcImpl.delete(enumId, key, operator);
		return this.dynamicEnumDaoMemoryImpl.delete(enumId, key, operator);
	}

	@Override
	public boolean update(DynamicEnumConstantEntity record, Operator operator) {
		dynamicEnumDaoJdbcImpl.update(record, operator);
		return this.dynamicEnumDaoMemoryImpl.update(record, operator);
	}

	@Override
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	@Override
	public boolean updateAll(List<DynamicEnumConstantEntity> allConstantList) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public DynamicEnumConstantEntity get(String enumId, String key) {
		return dynamicEnumDaoJdbcImpl.get(enumId, key);
	}

	@Override
	public boolean enable(String enumId, String key, Operator operator) {
		dynamicEnumDaoJdbcImpl.enable(enumId, key, operator);
		return this.dynamicEnumDaoMemoryImpl.enable(enumId, key, operator);
	}

	@Override
	public boolean disable(String enumId, String key, Operator operator) {
		dynamicEnumDaoJdbcImpl.disable(enumId, key, operator);
		return this.dynamicEnumDaoMemoryImpl.disable(enumId, key, operator);
	}

}
