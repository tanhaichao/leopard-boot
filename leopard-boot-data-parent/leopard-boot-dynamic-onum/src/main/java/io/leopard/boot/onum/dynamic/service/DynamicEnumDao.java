package io.leopard.boot.onum.dynamic.service;

import java.util.Date;
import java.util.List;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;

public interface DynamicEnumDao {

	/**
	 * 添加元素
	 * 
	 * @param record
	 * @return
	 */
	boolean add(DynamicEnumConstantEntity record, Operator operator);

	/**
	 * 删除枚举元素
	 * 
	 * @param enumId
	 * @param key
	 * @return
	 */
	boolean delete(String enumId, String key, Operator operator);

	/**
	 * 更新枚举元素
	 * 
	 * @param record
	 * @return
	 */
	boolean update(DynamicEnumConstantEntity record, Operator operator);

	boolean updateAll(List<DynamicEnumConstantEntity> allConstantList);

	/**
	 * 根据枚举ID查询所有元素记录
	 * 
	 * @param enumId
	 * @return
	 */
	List<DynamicEnumConstantEntity> list(String enumId);

	/**
	 * 获取所有枚举元素列表
	 * 
	 * @return
	 */
	List<DynamicEnumConstantEntity> listAll();

	/**
	 * 获取最后修改时间
	 * 
	 * @return
	 */
	Date getLastModifyTime();
	// void loadData();
}
