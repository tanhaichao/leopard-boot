package io.leopard.boot.kit.password;

import io.leopard.boot.util.EncryptUtil;

public class PasswordUtil {

	public static String encryptPassword(String password, String salt) {
		String str = password + "@" + salt;
		return EncryptUtil.sha1(str).toLowerCase();
	}
}
