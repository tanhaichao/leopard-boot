package io.leopard.boot.weixin;

/**
 * AccessToken过期
 * 
 * @author 谭海潮
 *
 */
public class InvalidCredentialException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public InvalidCredentialException(String message) {
		super(message);
	}
}
