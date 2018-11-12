package io.leopard.boot.requestmapping.custom;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * 请求头解析
 * 
 * @author 谭海潮
 *
 */
public interface RequestHeaderResolver {

	/**
	 * 解析头信息
	 * 
	 * @param method
	 * @param headers
	 */
	void resolve(Method method, Set<RequestHeaderMatcher> headerMatcherList);
}
