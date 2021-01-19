package io.leopard.boot.basicauth;

/**
 * 认证失败
 * 
 * @author 谭海潮
 *
 */
public class BasicAuthException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BasicAuthException(String message) {
		super(message);
	}
}
