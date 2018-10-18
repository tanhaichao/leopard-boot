package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistedException;

public class UserExistedException extends ExistedException {

	private static final long serialVersionUID = 1L;

	public UserExistedException(String username) {
		super("用户[" + username + "]已存在.");
	}
}
