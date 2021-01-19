package io.leopard.boot.basicauth;

/**
 * 认证未启用
 * 
 * @author 谭海潮
 *
 */
public class BasicAuthNotEnabledException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BasicAuthNotEnabledException(String message) {
		super(message);
	}
}
