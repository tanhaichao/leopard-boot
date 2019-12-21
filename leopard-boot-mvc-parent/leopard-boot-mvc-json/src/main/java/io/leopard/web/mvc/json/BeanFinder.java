package io.leopard.web.mvc.json;

/**
 * Spring Bean查找器
 * 
 * @author 谭海潮
 *
 */
public interface BeanFinder {
	Object findBean(Class<?> type);
}
