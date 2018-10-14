package io.leopard.boot.trynb.message;

/**
 * 错误信息解析
 * 
 * @author 谭海潮
 *
 */
public interface MessageParser {
	String parse(String message);
}