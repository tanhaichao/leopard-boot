package io.leopard.web.mvc.option;

import java.util.List;

/**
 * 选项数据解析器(给动态枚举用)
 * 
 * @author 谭海潮
 *
 */
public interface OptionDataResolver {

	/**
	 * 解析选项列表.
	 * 
	 * @param enumId 枚举ID
	 * @return
	 */
	List<OptionVO> resolve(String enumId) throws Exception;
}
