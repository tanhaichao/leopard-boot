package io.leopard.boot.onum.dynamic;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantEntity;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantForm;
import io.leopard.boot.onum.dynamic.model.DynamicEnumConstantVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;
import io.leopard.boot.onum.dynamic.model.DynamicEnumVO;
import io.leopard.boot.onum.dynamic.model.Operator;
import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
import io.leopard.boot.onum.dynamic.service.DynamicEnumService;
import io.leopard.boot.util.StreamUtil;
import io.leopard.lang.util.BeanUtil;

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
	 * @return
	 * @throws DynamicEnumNotFoundException
	 */
	@RequestMapping("add")
	@ResponseBody
	public boolean add(String enumId, DynamicEnumConstantForm form, HttpServletRequest request) throws DynamicEnumNotFoundException, Exception {
		checkDynamicEnumManageValidator();
		Operator operator = new Operator();
		this.dynamicEnumManageValidator.addEnumConstant(enumId, form, operator, request);

		if (!DynamicEnumManager.hasEnum(enumId)) {
			throw new DynamicEnumNotFoundException(enumId);
		}

		if (DynamicEnumManager.hasEnumConstant(enumId, form.getKey())) {
			throw new RuntimeException("枚举元素[enumId:" + enumId + " key:" + form.getKey() + "]已存在.");
		}

		DynamicEnumConstantEntity entity = new DynamicEnumConstantEntity();
		entity.setEnumId(enumId);
		entity.setKey(form.getKey());
		entity.setDesc(form.getDesc());
		entity.setPosition(form.getPosition());
		boolean success = dynamicEnumService.add(entity, operator);
		dynamicEnumService.rsync(enumId);
		return success;
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
		List<DynamicEnumConstantEntity> constantList = dynamicEnumService.list(enumId);
		List<DynamicEnumConstantVO> constantVOList = new ArrayList<>();
		if (constantList != null) {
			for (DynamicEnumConstantEntity constant : constantList) {
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
		boolean success = this.dynamicEnumService.delete(enumId, key, operator);
		dynamicEnumService.rsync(enumId);
		return success;
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
	public boolean update(String enumId, DynamicEnumConstantForm form, HttpServletRequest request) throws DynamicEnumNotFoundException, DynamicEnumConstantNotFoundException, Exception {
		checkDynamicEnumManageValidator();
		Operator operator = new Operator();
		this.dynamicEnumManageValidator.updateEnumConstant(enumId, form, operator, request);
		if (!DynamicEnumManager.hasEnum(enumId)) {
			throw new DynamicEnumNotFoundException(enumId);
		}
		if (!DynamicEnumManager.hasEnumConstant(enumId, form.getKey())) {
			throw new DynamicEnumConstantNotFoundException(enumId, form.getKey());
		}
		DynamicEnumConstantEntity entity = new DynamicEnumConstantEntity();
		entity.setEnumId(enumId);
		entity.setKey(form.getKey());
		entity.setDesc(form.getDesc());
		entity.setPosition(form.getPosition());
		boolean success = dynamicEnumService.update(entity, operator);
		dynamicEnumService.rsync(enumId);
		return success;
	}

	/**
	 * 批量更新
	 * 
	 * @param enumId 枚举ID
	 * @param constantList 枚举元素列表(所有)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("batchUpdate")
	@ResponseBody
	@Transactional
	public boolean batchUpdate(String enumId, List<DynamicEnumConstantForm> constantList, HttpServletRequest request) throws Exception {
		checkDynamicEnumManageValidator();

		List<DynamicEnumConstantEntity> constantEntityList = dynamicEnumService.list(enumId);
		// 数据库中的元素key列表
		List<String> keyList = StreamUtil.getFieldValueList(constantEntityList, DynamicEnumConstantEntity::getKey);
		// 需要删除的元素key列表
		List<String> deleteKeyList = StreamUtil.getDeletedFieldValueList(constantList, DynamicEnumConstantForm::getKey, keyList);

		int position = 1;
		for (DynamicEnumConstantForm form : constantList) {
			boolean contains = keyList.contains(form.getKey());
			if (contains) {// 更新
				Operator operator = new Operator();
				this.dynamicEnumManageValidator.updateEnumConstant(enumId, form, operator, request);

				DynamicEnumConstantEntity entity = this.dynamicEnumService.get(enumId, form.getKey());
				BeanUtil.copyProperties(form, entity);
				dynamicEnumService.update(entity, operator);
			}
			else {// 新增
				Operator operator = new Operator();
				this.dynamicEnumManageValidator.addEnumConstant(enumId, form, operator, request);

				DynamicEnumConstantEntity entity = BeanUtil.convert(form, DynamicEnumConstantEntity.class);
				entity.setPosition(position);
				dynamicEnumService.add(entity, operator);
			}
			position++;
		}

		// 删除枚举元素
		for (String key : deleteKeyList) {
			Operator operator = new Operator();
			this.dynamicEnumManageValidator.deleteEnumConstant(enumId, key, operator, request);
			dynamicEnumService.delete(enumId, key, operator);
		}
		dynamicEnumService.rsync(enumId);
		return true;
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
