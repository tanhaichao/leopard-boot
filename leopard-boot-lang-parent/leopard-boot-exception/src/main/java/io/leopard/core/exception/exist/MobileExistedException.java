package io.leopard.core.exception.exist;

import io.leopard.core.exception.ExistedException;

/**
 * 手机号码已存在.
 * 
 * @author 谭海潮
 *
 */
public class MobileExistedException extends ExistedException {

	private static final long serialVersionUID = 1L;

	public MobileExistedException(String mobile) {
		super("手机号码[" + mobile + "]已存在.");
	}

}
