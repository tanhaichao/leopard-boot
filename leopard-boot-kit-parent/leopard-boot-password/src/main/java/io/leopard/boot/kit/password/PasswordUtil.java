package io.leopard.boot.kit.password;

import io.leopard.boot.util.EncryptUtil;
import io.leopard.boot.util.NumberUtil;

public class PasswordUtil {

	public static String encryptPassword(String password, String salt) {
		String str = password + "@" + salt;
		return EncryptUtil.sha1(str).toLowerCase();
	}

	private static final String passwordChars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789_-$%&@+!";

	/**
	 * 生成随机密码
	 * 
	 * @param length 密码长度
	 */
	public static String generatePassword(int length) {
		StringBuilder sb = new StringBuilder();
		int passwordCharsLen = passwordChars.length();
		for (int i = 0; i < length; i++) {
			int x = NumberUtil.random(passwordCharsLen);
			sb.append(passwordChars.charAt(x));
		}
		return sb.toString();
	}
}
