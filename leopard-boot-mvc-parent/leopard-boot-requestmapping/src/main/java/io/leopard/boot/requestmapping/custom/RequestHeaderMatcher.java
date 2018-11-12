package io.leopard.boot.requestmapping.custom;

import javax.servlet.http.HttpServletRequest;

/**
 * 请求头匹配器
 * 
 * @author 谭海潮
 *
 */
public interface RequestHeaderMatcher {

	/**
	 * 获取头信息
	 * 
	 * @return
	 */
	String getHeader();

	/**
	 * 是否有限匹配
	 * 
	 * @return
	 */
	boolean isFirstLookup();

	boolean match(HttpServletRequest request);
}
