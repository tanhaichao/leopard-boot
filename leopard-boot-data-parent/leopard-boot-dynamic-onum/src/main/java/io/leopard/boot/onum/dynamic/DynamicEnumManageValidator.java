package io.leopard.boot.onum.dynamic;

import javax.servlet.http.HttpServletRequest;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantForm;
import io.leopard.boot.onum.dynamic.model.Operator;

/**
 * 动态枚举管理验证器
 * 
 * @author 谭海潮
 *
 */
public interface DynamicEnumManageValidator {
	/**
	 * 新增枚举元素
	 * 
	 * @param enumId 枚举ID
	 * @param constant 枚举元素信息
	 * @return
	 * @throws Exception
	 */
	void addEnumConstant(String enumId, DynamicEnumConstantForm constant, Operator operator, HttpServletRequest request) throws Exception;

	/**
	 * 获取动态枚举详情
	 * 
	 * @param enumId
	 * @param operator
	 * @param request
	 * @return
	 * @throws Exception
	 */
	void getEnum(String enumId, HttpServletRequest request) throws Exception;

	/**
	 * 删除枚举元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 枚举元素key
	 * @param operator 操作人
	 * @param request
	 * @return
	 * @throws Exception
	 */
	void deleteEnumConstant(String enumId, String key, Operator operator, HttpServletRequest request) throws Exception;

	/**
	 * 修改
	 * 
	 * @param enumId 枚举ID
	 * @param constant 枚举元素信息
	 * @param operator 操作人
	 * @return
	 * @throws Exception
	 */
	void updateEnumConstant(DynamicEnumConstantForm constant, Operator operator, HttpServletRequest request) throws Exception;

}
