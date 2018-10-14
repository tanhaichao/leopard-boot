package io.leopard.boot.onum.dynamic.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.stereotype.Repository;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.Operator;

@Repository
public class DynamicEnumDaoMemoryImpl implements DynamicEnumDao {
	Map<String, List<DynamicEnumEntity>> enumMap = new ConcurrentHashMap<>();

	@Override
	public List<DynamicEnumEntity> list(String enumId) {
		return enumMap.get(enumId);
	}

	@Override
	public List<DynamicEnumEntity> listAll() {
		List<DynamicEnumEntity> allConstantList = new ArrayList<>();
		for (Entry<String, List<DynamicEnumEntity>> dynamicEnum : enumMap.entrySet()) {
			allConstantList.addAll(dynamicEnum.getValue());
		}
		return allConstantList;
	}

	@Override
	public boolean add(DynamicEnumEntity record, Operator operator) {
		String enumId = record.getEnumId();
		List<DynamicEnumEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			constantList = new ArrayList<>();
			enumMap.put(enumId, constantList);
		}
		{
			for (DynamicEnumEntity old : constantList) {
				if (old.getKey().equals(record.getKey())) {
					throw new RuntimeException("枚举元素[" + record.getKey() + "]已存在.");
				}
			}
		}

		return constantList.add(record);
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		List<DynamicEnumEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + enumId + "]不存在.");
		}
		Iterator<DynamicEnumEntity> iterator = constantList.iterator();
		while (iterator.hasNext()) {
			DynamicEnumEntity constant = iterator.next();
			if (constant.getKey().equals(key)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean update(DynamicEnumEntity record, Operator operator) {
		List<DynamicEnumEntity> constantList = enumMap.get(record.getEnumId());
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + record.getEnumId() + "]不存在.");
		}
		{
			for (DynamicEnumEntity old : constantList) {
				if (old.getKey().equals(record.getKey())) {
					old.setPosition(record.getPosition());
					old.setDesc(record.getDesc());
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public Date getLastModifyTime() {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public boolean updateAll(List<DynamicEnumEntity> allConstantList) {
		this.enumMap.clear();
		if (allConstantList == null || allConstantList.isEmpty()) {
			return true;
		}
		Map<String, List<DynamicEnumEntity>> enumMap = allConstantList.stream().collect(Collectors.groupingBy(DynamicEnumEntity::getEnumId));
		this.enumMap.putAll(enumMap);
		return false;
	}
}
