package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistedException;

/**
 * 邮箱已存在.
 * 
 * @author 谭海潮
 *
 */
public class EmailExistedException extends ExistedException {

	private static final long serialVersionUID = 1L;

	public EmailExistedException(String email) {
		super("邮箱[" + email + "]已存在.");
	}

}
