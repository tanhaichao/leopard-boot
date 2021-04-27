package io.leopard.boot.util;

import io.leopard.core.exception.invalid.MobileInvalidException;
import io.leopard.core.exception.invalid.PassportInvalidException;
import io.leopard.core.exception.invalid.UidInvalidException;
import io.leopard.core.exception.invalid.UsernameInvalidException;

/**
 * 参数合法性判断(出错抛异常)..
 * 
 * @author 阿海
 * 
 */
public class LeopardCheckUtil {

	// private static final Log logger = LogFactory.getLog(LeopardCheckUtil.class);
	/**
	 * 是否合法的用户名..
	 * 
	 * @param username
	 */
	public static void isUsername(String username) {
		if (!LeopardValidUtil.isValidUsername(username)) {
			throw new UsernameInvalidException(username);
		}
	}

	/**
	 * 是否合法的passport .
	 * 
	 * @param passport
	 */
	public static void isValidPassport(String passport) {
		if (!LeopardValidUtil.isValidPassport(passport)) {
			throw new PassportInvalidException(passport);
		}
	}

	/**
	 * 是否合法的用户名..
	 * 
	 * @param username
	 */
	public static void isValidUsername(String username) {
		if (!LeopardValidUtil.isValidUsername(username)) {
			throw new UsernameInvalidException(username);
		}
	}

	/**
	 * 是否合法的UID.
	 * 
	 * @param username
	 */
	public static void isUid(long uid) {
		if (!LeopardValidUtil.isValidUid(uid)) {
			throw new UidInvalidException(uid);
		}
	}

	/**
	 * 是否合法的UID.
	 * 
	 * @param username
	 */
	public static void isValidUid(long uid) {
		if (!LeopardValidUtil.isValidUid(uid)) {
			throw new UidInvalidException(uid);
		}
	}

	/**
	 * 是否合法的手机号
	 * 
	 * @param mobile
	 */
	public static void isMobile(String mobile) {
		if (!LeopardValidUtil.isMobile(mobile)) {
			throw new MobileInvalidException(mobile);
		}
	}
}
