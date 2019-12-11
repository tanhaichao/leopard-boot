package io.leopard.lang.datatype;

/**
 * 时间排序(可按posttime或lmodify排序)
 * 
 * @author 谭海潮
 *
 */
public class TimeSort extends Sort {

	@Override
	public String[] allowFieldNames() {
		return new String[] { "posttime", "lmodify" };
	}
}
