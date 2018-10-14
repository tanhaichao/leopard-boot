package io.leopard.boot.xparam;

/**
 * 未登录.
 * 
 * @author 阿海
 * 
 */
public class NotLoginException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NotLoginException(String message) {
		super(message);
	}
}
