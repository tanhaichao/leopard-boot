package io.leopard.boot.trynb;

import javax.servlet.http.HttpServletRequest;

/**
 * 错误消息过滤器
 * 
 * @author 谭海潮
 *
 */
public interface ErrorMessageFilter {

	String parseMessage(HttpServletRequest request, Throwable e);
}
