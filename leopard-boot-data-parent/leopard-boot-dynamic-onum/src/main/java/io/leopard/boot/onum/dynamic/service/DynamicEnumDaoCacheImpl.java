package io.leopard.boot.onum.dynamic.service;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
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
		List<DynamicEnumEntity> allConstantList = dynamicEnumDaoJdbcImpl.listAll();
		dynamicEnumDaoMemoryImpl.updateAll(allConstantList);
	}

	@Override
	public List<DynamicEnumEntity> list(String enumId) {
		List<DynamicEnumEntity> constantList = dynamicEnumDaoMemoryImpl.list(enumId);
		if (constantList != null) {
			Collections.sort(constantList, new DynamicEnumRecordComparator());
		}
		return constantList;
	}

	@Override
	public List<DynamicEnumEntity> listAll() {
		return dynamicEnumDaoJdbcImpl.listAll();
	}

	@Override
	public boolean add(DynamicEnumEntity record, Operator operator) {
		this.dynamicEnumDaoJdbcImpl.add(record, operator);
		return dynamicEnumDaoMemoryImpl.add(record, operator);
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		dynamicEnumDaoJdbcImpl.delete(enumId, key, operator);
		return this.dynamicEnumDaoMemoryImpl.delete(enumId, key, operator);
	}

	@Override
	public boolean update(DynamicEnumEntity record, Operator operator) {
		dynamicEnumDaoJdbcImpl.update(record, operator);
		return this.dynamicEnumDaoMemoryImpl.update(record, operator);
	}

	@Override
	public Date getLastModifyTime() {
		return lastModifyTime;
	}

	@Override
	public boolean updateAll(List<DynamicEnumEntity> allConstantList) {
		throw new NotImplementedException("not impl.");
	}

}
