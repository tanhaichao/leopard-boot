package io.leopard.boot.onum.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.model.OptionVO;
import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
import io.leopard.boot.onum.dynamic.service.DynamicEnumService;
import io.leopard.lang.inum.dynamic.EnumConstant;

/**
 * 动态枚举管理
 * 
 * @author 谭海潮
 *
 */
@Controller
public class DynamicEnumOptionController {

	@Autowired
	private DynamicEnumService dynamicEnumService;

	@RequestMapping("/dynamicEnum/{enumId}")
	@ResponseBody
	public List<OptionVO> options(@PathVariable String enumId) throws DynamicEnumNotFoundException {
		if (!DynamicEnumManager.hasEnum(enumId)) {
			throw new DynamicEnumNotFoundException(enumId);
		}
		List<DynamicEnumEntity> constantList = dynamicEnumService.list(enumId);

		List<EnumConstant> constants = DynamicEnumManager.listByEnumId(enumId);

		if (constantList.size() != constants.size()) {
			throw new RuntimeException("枚举[" + enumId + "]元素数量不一致.");
		}

		List<OptionVO> optionVOList = new ArrayList<>();
		if (constantList != null) {
			for (DynamicEnumEntity constant : constantList) {
				OptionVO optionVO = new OptionVO();
				optionVO.setKey(constant.getKey());
				optionVO.setDesc(constant.getDesc());
				optionVOList.add(optionVO);
			}
		}
		return optionVOList;
	}

}
