package io.leopard.core.exception.other;

import io.leopard.core.exception.ForbiddenException;

/**
 * 次数已到上限
 * 
 * @author 谭海潮
 *
 */
public class MaxCountException extends ForbiddenException {

	private static final long serialVersionUID = 1L;

	public MaxCountException(long uid, int max) {
		super("次数已到上限[" + max + "." + uid + "].");
	}

	public MaxCountException(String message) {
		super(message);
	}
}
