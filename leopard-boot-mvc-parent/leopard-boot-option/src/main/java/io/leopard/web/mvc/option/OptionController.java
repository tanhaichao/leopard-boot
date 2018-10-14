package io.leopard.web.mvc.option;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.lang.inum.EnumUtil;
import io.leopard.lang.inum.JsonField;
import io.leopard.lang.inum.Onum;
import io.leopard.lang.inum.SubEnum;

@Controller
public class OptionController {

	@Autowired(required = false)
	private OptionDataResolver optionDataResolver;
	// @RequestMapping("enum/{id}")
	// @ResponseBody
	// public List<OptionVO> onum(@PathVariable String id) throws OptionNotFoundException {
	// List<OptionVO> data = OptionData.getData(id);
	// if (data == null) {
	// throw new OptionNotFoundException("枚举[" + id + "]不存在.");
	// }
	// return data;
	// }

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping("enum/{enumId}")
	@ResponseBody
	public List<Map<String, Object>> onum(@PathVariable String enumId) throws OptionNotFoundException, Exception {
		OptionInfo optionInfo = OptionData.getOptionInfo(enumId);
		if (optionInfo == null) {
			List<Map<String, Object>> list = this.resolveOnum(enumId);// 解析动态枚举
			if (list != null) {
				return list;
			}
			throw new OptionNotFoundException("枚举[" + enumId + "]不存在.");
		}

		// TODO 缓存
		Class<? extends Enum> clazz;
		try {
			clazz = (Class<? extends Enum>) Class.forName(optionInfo.getClassName());
		}
		catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage(), e);
		}

		boolean isSubEnum = SubEnum.class.isAssignableFrom(clazz);
		Map<Object, Enum<?>> map = EnumUtil.toMap(clazz);

		List<Field> jsonFieldList = this.listJsonField(clazz);

		List<Map<String, Object>> list = new ArrayList<>();
		for (Entry<Object, Enum<?>> entry : map.entrySet()) {
			Object key = entry.getKey();
			Onum onum = (Onum) entry.getValue();
			String desc = (String) onum.getDesc();
			Map<String, Object> constant = new LinkedHashMap<String, Object>();
			constant.put("key", key);
			constant.put("desc", desc);

			// @JsonField
			for (Field field : jsonFieldList) {
				field.setAccessible(true);
				Object value;
				try {
					value = field.get(onum);
				}
				catch (IllegalAccessException e) {
					throw new RuntimeException(e.getMessage(), e);
				}
				String fieldName = field.getName();
				constant.put(fieldName, value);
			}

			if (isSubEnum) {// 子枚举还不支持@JsonField
				List<OptionVO> childList = OptionData.getChildList(onum);
				if (childList != null && !childList.isEmpty()) {
					constant.put("childList", childList);
				}
			}
			list.add(constant);
		}

		return list;
	}

	/**
	 * 解析动态枚举
	 * 
	 * @param enumId
	 * @return
	 * @throws Exception
	 */
	protected List<Map<String, Object>> resolveOnum(String enumId) throws Exception {
		if (optionDataResolver == null) {
			return null;
		}
		List<OptionVO> optionVOList = optionDataResolver.resolve(enumId);
		if (optionVOList == null || optionVOList.isEmpty()) {
			return null;
		}
		List<Map<String, Object>> optionList = new ArrayList<>();
		for (OptionVO optionVO : optionVOList) {
			Map<String, Object> constant = new LinkedHashMap<String, Object>();
			constant.put("key", optionVO.getKey());
			constant.put("desc", optionVO.getDesc());
			optionList.add(constant);
		}
		return optionList;
	}

	protected List<Field> listJsonField(Class<?> clazz) {
		// TODO 缓存
		List<Field> fieldList = new ArrayList<Field>();
		for (Field field : clazz.getDeclaredFields()) {
			JsonField anno = field.getAnnotation(JsonField.class);
			if (anno == null) {
				continue;
			}
			fieldList.add(field);
		}
		return fieldList;
	}

	@RequestMapping("enum/list")
	@ResponseBody
	public Map<String, List<OptionVO>> list(List<String> idList) throws OptionNotFoundException, Exception {
		if (idList == null || idList.isEmpty()) {
			throw new IllegalArgumentException("idList不能为空.");
		}
		Map<String, List<OptionVO>> map = new LinkedHashMap<String, List<OptionVO>>();
		for (String enumId : idList) {
			List<OptionVO> data = OptionData.getData(enumId);
			if (data == null && optionDataResolver != null) {// 解析动态枚举
				data = optionDataResolver.resolve(enumId);
			}
			if (data == null) {
				throw new OptionNotFoundException("枚举[" + enumId + "]不存在.");
			}
			map.put(enumId, data);
		}
		return map;
	}

}
