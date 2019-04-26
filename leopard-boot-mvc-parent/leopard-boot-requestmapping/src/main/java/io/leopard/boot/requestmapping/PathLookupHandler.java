package io.leopard.boot.requestmapping;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.method.HandlerMethod;

public interface PathLookupHandler {

	/**
	 * 是否忽略的地址
	 * 
	 * @param lookupPath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	HandlerMethod transform(String lookupPath, HttpServletRequest request, HandlerMethod method) throws Exception;

}
