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

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;

@Repository
public class DynamicEnumDaoMemoryImpl implements DynamicEnumDao {
	Map<String, List<DynamicEnumConstantEntity>> enumMap = new ConcurrentHashMap<>();

	@Override
	public List<DynamicEnumConstantEntity> list(String enumId) {
		return enumMap.get(enumId);
	}

	@Override
	public List<DynamicEnumConstantEntity> listAll() {
		List<DynamicEnumConstantEntity> allConstantList = new ArrayList<>();
		for (Entry<String, List<DynamicEnumConstantEntity>> dynamicEnum : enumMap.entrySet()) {
			allConstantList.addAll(dynamicEnum.getValue());
		}
		return allConstantList;
	}

	@Override
	public boolean add(DynamicEnumConstantEntity record, Operator operator) {
		String enumId = record.getEnumId();
		List<DynamicEnumConstantEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			constantList = new ArrayList<>();
			enumMap.put(enumId, constantList);
		}
		{
			for (DynamicEnumConstantEntity old : constantList) {
				if (old.getKey().equals(record.getKey())) {
					throw new RuntimeException("枚举元素[" + record.getKey() + "]已存在.");
				}
			}
		}

		return constantList.add(record);
	}

	@Override
	public boolean delete(String enumId, String key, Operator operator) {
		List<DynamicEnumConstantEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + enumId + "]不存在.");
		}
		Iterator<DynamicEnumConstantEntity> iterator = constantList.iterator();
		while (iterator.hasNext()) {
			DynamicEnumConstantEntity constant = iterator.next();
			if (constant.getKey().equals(key)) {
				iterator.remove();
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean update(DynamicEnumConstantEntity record, Operator operator) {
		List<DynamicEnumConstantEntity> constantList = enumMap.get(record.getEnumId());
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + record.getEnumId() + "]不存在.");
		}
		{
			for (DynamicEnumConstantEntity old : constantList) {
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
	public boolean updateAll(List<DynamicEnumConstantEntity> allConstantList) {
		this.enumMap.clear();
		if (allConstantList == null || allConstantList.isEmpty()) {
			return true;
		}
		Map<String, List<DynamicEnumConstantEntity>> enumMap = allConstantList.stream().collect(Collectors.groupingBy(DynamicEnumConstantEntity::getEnumId));
		this.enumMap.putAll(enumMap);
		return false;
	}

	@Override
	public DynamicEnumConstantEntity get(String enumId, String key) {
		throw new NotImplementedException("not impl.");
	}

	@Override
	public boolean enable(String enumId, String key, Operator operator) {
		List<DynamicEnumConstantEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + enumId + "]不存在.");
		}
		Iterator<DynamicEnumConstantEntity> iterator = constantList.iterator();
		while (iterator.hasNext()) {
			DynamicEnumConstantEntity constant = iterator.next();
			if (constant.getKey().equals(key)) {
				constant.setDisable(false);
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean disable(String enumId, String key, Operator operator) {
		List<DynamicEnumConstantEntity> constantList = enumMap.get(enumId);
		if (constantList == null) {
			throw new RuntimeException("动态枚举[" + enumId + "]不存在.");
		}
		Iterator<DynamicEnumConstantEntity> iterator = constantList.iterator();
		while (iterator.hasNext()) {
			DynamicEnumConstantEntity constant = iterator.next();
			if (constant.getKey().equals(key)) {
				constant.setDisable(true);
				return true;
			}
		}
		return false;
	}
}
