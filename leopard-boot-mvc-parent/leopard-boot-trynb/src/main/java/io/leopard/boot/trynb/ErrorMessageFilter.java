package io.leopard.boot.trynb;

/**
 * 错误消息过滤器
 * 
 * @author 谭海潮
 *
 */
public interface ErrorMessageFilter {

	String parseMessage(Throwable e);
}
