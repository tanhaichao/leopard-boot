package io.leopard.boot.basicauth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.leopard.boot.util.Base64;

@Component("leopardBasicAuthService")
public class AuthService {
	/**
	 * 登录名
	 */
	@Value("${auth.username:}")
	private String username;

	/**
	 * 登录密码
	 */
	@Value("${auth.password:}")
	private String password;

	/**
	 * 检查是否合法登录
	 * 
	 * @param request 请求对象
	 * @return 是否合法登录
	 */
	public boolean checkAuth(HttpServletRequest request) {
		return checkAuth(request.getHeader("Authorization"), username, password);
	}

	/**
	 * 是否不合法的数组
	 * 
	 * @param arr
	 * @return 是否不合法的数组
	 */
	private static boolean isBadArray(String[] arr) {
		return arr == null || arr.length != 2;
	}

	/**
	 * 检查是否合法登录
	 * 
	 * @param authorization 认证后每次HTTP请求都会附带上 Authorization 头信息
	 * @param username 用户名
	 * @param password 密码
	 * @return true = 认证成功/ false = 需要认证
	 */
	public boolean checkAuth(String authorization, String username, String password) {
		if (StringUtils.isEmpty(authorization)) {
			return false;
		}

		String[] basicArray = authorization.split("\\s+");
		if (isBadArray(basicArray)) {
			return false;
		}

		String idpass = Base64.decode(basicArray[1]);

		// String idpass = Encode.base64Decode(basicArray[1]);
		if (StringUtils.isEmpty(idpass))
			return false;

		String[] idpassArray = idpass.split(":");
		if (isBadArray(idpassArray)) {
			return false;
		}
		return username.equalsIgnoreCase(idpassArray[0]) && password.equalsIgnoreCase(idpassArray[1]);
	}
}
