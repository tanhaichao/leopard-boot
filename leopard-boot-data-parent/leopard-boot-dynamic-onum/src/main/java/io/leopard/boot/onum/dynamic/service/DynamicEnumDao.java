package io.leopard.boot.onum.dynamic.service;

import java.util.Date;
import java.util.List;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;

public interface DynamicEnumDao {

	/**
	 * 添加元素
	 * 
	 * @param entity
	 * @return
	 */
	boolean add(DynamicEnumConstantEntity entity, Operator operator);

	/**
	 * 删除枚举元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 * @return
	 */
	boolean delete(String enumId, String key, Operator operator);

	/**
	 * 获取枚举元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 * @return
	 */
	DynamicEnumConstantEntity get(String enumId, String key);

	/**
	 * 更新枚举元素
	 * 
	 * @param entity
	 * @return
	 */
	boolean update(DynamicEnumConstantEntity entity, Operator operator);

	boolean updateAll(List<DynamicEnumConstantEntity> allConstantList);

	/**
	 * 根据枚举ID查询所有元素记录
	 * 
	 * @param enumId 枚举ID
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

	/**
	 * 启用
	 * 
	 * @param enumId 枚举ID
	 * @param key 元素key
	 * @param operator
	 * @return
	 */
	boolean enable(String enumId, String key, Operator operator);

	/**
	 * 禁用
	 * 
	 * @param enumId 枚举ID
	 * @param key 元素key
	 * @param operator
	 * @return
	 */
	boolean disable(String enumId, String key, Operator operator);
}
