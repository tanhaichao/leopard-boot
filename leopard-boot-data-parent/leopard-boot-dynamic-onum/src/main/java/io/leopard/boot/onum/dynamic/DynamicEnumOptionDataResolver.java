package io.leopard.boot.onum.dynamic;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.boot.onum.dynamic.model.DynamicEnumEntity;
import io.leopard.boot.onum.dynamic.service.DynamicEnumManager;
import io.leopard.boot.onum.dynamic.service.DynamicEnumService;
import io.leopard.lang.inum.dynamic.EnumConstant;
import io.leopard.web.mvc.option.OptionDataResolver;
import io.leopard.web.mvc.option.OptionVO;

@Component
public class DynamicEnumOptionDataResolver implements OptionDataResolver {
	@Autowired
	private DynamicEnumService dynamicEnumService;

	@Override
	public List<OptionVO> resolve(String enumId) {
		if (!DynamicEnumManager.hasEnum(enumId)) {
			// throw new DynamicEnumNotFoundException(enumId);
			return null;
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
