package io.leopard.boot.requestmapping;

import javax.servlet.http.HttpServletRequest;

public interface PathLookupHandler {

	/**
	 * 是否忽略的地址
	 * 
	 * @param lookupPath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	boolean isIgnore(String lookupPath, HttpServletRequest request) throws Exception;

}
