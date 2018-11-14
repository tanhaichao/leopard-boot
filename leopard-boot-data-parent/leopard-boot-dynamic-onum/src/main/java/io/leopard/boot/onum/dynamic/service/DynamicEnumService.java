package io.leopard.boot.onum.dynamic.service;

import java.util.List;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.lang.inum.dynamic.EnumConstant;

public interface DynamicEnumService {
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
	 * 根据枚举ID查询所有元素记录
	 * 
	 * @param enumId 枚举ID
	 * @return
	 */
	List<DynamicEnumConstantEntity> list(String enumId);

	/**
	 * 更新枚举元素
	 * 
	 * @param entity
	 * @return
	 */
	boolean update(DynamicEnumConstantEntity entity, Operator operator);

	DynamicEnumDataVO get();

	/**
	 * 获取枚举元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 * @return
	 */
	DynamicEnumConstantEntity get(String enumId, String key);

	List<EnumConstant> resolve(String enumId, Class<?> enumType);

	/**
	 * 同步所有枚举
	 * 
	 * @return
	 */
	boolean rsyncAll();

	/**
	 * 同步单个枚举
	 * 
	 * @param enumId 枚举ID
	 * @return
	 */
	boolean rsync(String enumId);

	/**
	 * 只返回已启用的元素
	 * 
	 * @param enumId
	 * @return
	 */
	List<DynamicEnumConstantEntity> listEnableEnumConstant(String enumId);

	// void setEnumList(List<DynamicEnumInfo> enumList);

}
