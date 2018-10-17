package io.leopard.boot.onum.dynamic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantForm;
import io.leopard.boot.onum.dynamic.model.DynamicEnumVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantVO;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
import io.leopard.boot.onum.dynamic.service.DynamicEnumService;

/**
 * 动态枚举管理
 * 
 * @author 谭海潮
 *
 */
@Controller
@RequestMapping("/dynamicEnum/manage/")
public class DynamicEnumManageController {

	@Autowired
	private DynamicEnumService dynamicEnumService;

	@Autowired(required = false)
	private DynamicEnumManageValidator dynamicEnumManageValidator;

	/**
	 * 检查动态枚举管理验证器是否已定义
	 */
	protected void checkDynamicEnumManageValidator() {
		// TODO 这里可以判断是否单元测试环境?
		if (dynamicEnumManageValidator == null) {
			throw new RuntimeException("未配置动态枚举验证器.");
		}
	}

	/**
	 * 添加
	 * 
	 * @param enumId 枚举ID
	 * @param key 元素key
	 * @param desc 元素描述
	 * @return
	 * @throws DynamicEnumNotFoundException
	 */
	@RequestMapping("add")
	@ResponseBody
	public boolean add(DynamicEnumConstantForm form, HttpServletRequest request) throws DynamicEnumNotFoundException, Exception {
		checkDynamicEnumManageValidator();
		Operator operator = new Operator();
		this.dynamicEnumManageValidator.addEnumConstant(form, operator, request);

		if (!DynamicEnumManager.hasEnum(form.getEnumId())) {
			throw new DynamicEnumNotFoundException(form.getEnumId());
		}

		if (DynamicEnumManager.hasEnumConstant(form.getEnumId(), form.getKey())) {
			throw new RuntimeException("枚举元素[enumId:" + form.getEnumId() + " key:" + form.getKey() + "]已存在.");
		}

		DynamicEnumEntity entity = new DynamicEnumEntity();
		entity.setEnumId(form.getEnumId());
		entity.setKey(form.getKey());
		entity.setDesc(form.getDesc());
		entity.setPosition(form.getPosition());
		return dynamicEnumService.add(entity, operator);
	}

	/**
	 * 获取动态枚举详情
	 * 
	 * @param enumId 枚举ID
	 * @return
	 * @throws DynamicEnumNotFoundException
	 */
	@RequestMapping("get")
	@ResponseBody
	public DynamicEnumVO get(String enumId, HttpServletRequest request) throws DynamicEnumNotFoundException, Exception {
		this.checkDynamicEnumManageValidator();
		this.dynamicEnumManageValidator.getEnum(enumId, request);
		if (!DynamicEnumManager.hasEnum(enumId)) {
			throw new DynamicEnumNotFoundException(enumId);
		}
		List<DynamicEnumEntity> constantList = dynamicEnumService.list(enumId);
		List<DynamicEnumConstantVO> constantVOList = new ArrayList<>();
		if (constantList != null) {
			for (DynamicEnumEntity constant : constantList) {
				DynamicEnumConstantVO constantVO = new DynamicEnumConstantVO();
				constantVO.setKey(constant.getKey());
				constantVO.setDesc(constant.getDesc());
				constantVOList.add(constantVO);
			}
		}
		DynamicEnumVO dynamicEnumVO = new DynamicEnumVO();
		dynamicEnumVO.setEnumId(enumId);
		dynamicEnumVO.setConstantList(constantVOList);
		return dynamicEnumVO;
	}

	/**
	 * 删除元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 元素key
	 * @return
	 * @throws DynamicEnumNotFoundException
	 * @throws DynamicEnumConstantNotFoundException
	 */
	@RequestMapping("delete")
	@ResponseBody
	public boolean delete(String enumId, String key, HttpServletRequest request) throws DynamicEnumNotFoundException, DynamicEnumConstantNotFoundException, Exception {
		checkDynamicEnumManageValidator();
		Operator operator = new Operator();
		this.dynamicEnumManageValidator.deleteEnumConstant(enumId, key, operator, request);
		if (!DynamicEnumManager.hasEnum(enumId)) {
			throw new DynamicEnumNotFoundException(enumId);
		}
		if (!DynamicEnumManager.hasEnumConstant(enumId, key)) {
			throw new DynamicEnumConstantNotFoundException(enumId, key);
		}
		return this.dynamicEnumService.delete(enumId, key, operator);
	}

	/**
	 * 更新元素
	 * 
	 * @param enumId 枚举ID
	 * @param key 元素key
	 * @param desc 元素描述
	 * @return
	 * @throws DynamicEnumNotFoundException
	 * @throws DynamicEnumConstantNotFoundException
	 */
	@RequestMapping("update")
	@ResponseBody
	public boolean update(DynamicEnumConstantForm form, HttpServletRequest request) throws DynamicEnumNotFoundException, DynamicEnumConstantNotFoundException, Exception {
		checkDynamicEnumManageValidator();
		Operator operator = new Operator();
		this.dynamicEnumManageValidator.updateEnumConstant(form, operator, request);
		if (!DynamicEnumManager.hasEnum(form.getEnumId())) {
			throw new DynamicEnumNotFoundException(form.getEnumId());
		}
		if (!DynamicEnumManager.hasEnumConstant(form.getEnumId(), form.getKey())) {
			throw new DynamicEnumConstantNotFoundException(form.getEnumId(), form.getKey());
		}
		DynamicEnumEntity entity = new DynamicEnumEntity();
		entity.setEnumId(form.getEnumId());
		entity.setKey(form.getKey());
		entity.setDesc(form.getDesc());
		entity.setPosition(form.getPosition());
		return dynamicEnumService.update(entity, operator);
	}

	/**
	 * 获取动态枚举信息
	 * 
	 * @return
	 */
	@RequestMapping
	@ResponseBody
	public DynamicEnumDataVO info(HttpServletRequest request) {
		checkDynamicEnumManageValidator();
		return dynamicEnumService.get();
	}
}
