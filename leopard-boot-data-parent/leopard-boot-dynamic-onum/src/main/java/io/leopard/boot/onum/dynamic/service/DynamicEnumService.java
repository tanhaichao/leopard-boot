package io.leopard.boot.onum.dynamic.service;

import java.util.List;

import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.lang.inum.dynamic.EnumConstant;

public interface DynamicEnumService {
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
	 * 根据枚举ID查询所有元素记录
	 * 
	 * @param enumId
	 * @return
	 */
	List<DynamicEnumConstantEntity> list(String enumId);

	/**
	 * 更新枚举元素
	 * 
	 * @param record
	 * @return
	 */
	boolean update(DynamicEnumConstantEntity record, Operator operator);

	boolean rsync();

	DynamicEnumDataVO get();

	List<EnumConstant> resolve(String enumId, Class<?> enumType);

	boolean update(String enumId);

	// void setEnumList(List<DynamicEnumInfo> enumList);

}
