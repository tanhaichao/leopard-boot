package io.leopard.boot.onum.dynamic;

import io.leopard.boot.onum.dynamic.model.DynamicEnumDataVO;

/**
 * 动态枚举解析器
 * 
 * @author 谭海潮
 *
 */
@Deprecated
public interface DynamicEnumResolver {

	boolean update();

	DynamicEnumDataVO get();

	boolean publish();

}
