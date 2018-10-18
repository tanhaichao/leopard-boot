package io.leopard.core.exception;

/**
 * 记录已存在异常.
 * 
 * @author 阿海
 * 
 */
public class ExistedException extends LeopardException {

	private static final long serialVersionUID = 1L;

	public ExistedException(String message) {
		super(message);
	}
}
