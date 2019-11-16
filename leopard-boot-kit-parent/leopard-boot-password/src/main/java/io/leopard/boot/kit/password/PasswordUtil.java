package io.leopard.boot.kit.password;

import io.leopard.boot.util.EncryptUtil;
import io.leopard.boot.util.StringUtil;

public class PasswordUtil {

	public static String encryptPassword(String password, int saltLength) {
		if (saltLength < 1) {
			throw new IllegalArgumentException("saltLength必须大于0.");
		}
		if (saltLength > 32) {
			throw new IllegalArgumentException("saltLength不能大于32.");
		}
		String salt = StringUtil.uuid().substring(0, saltLength);
		return encryptPassword(password, salt);
	}

	public static String encryptPassword(String password, String salt) {
		String str = password + "@" + salt;
		return EncryptUtil.sha1(str).toLowerCase();
	}
}
