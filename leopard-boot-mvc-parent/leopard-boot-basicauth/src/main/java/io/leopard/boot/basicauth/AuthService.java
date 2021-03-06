package io.leopard.boot.basicauth;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.leopard.boot.util.Base64;

@Component("leopardBasicAuthService")
public class AuthService {

	protected Log logger = LogFactory.getLog(this.getClass());

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

	public boolean isEnableAuth() {
		// logger.info("isEnableAuth username:" + username + " password:" + password);
		if (StringUtils.isEmpty(username)) {
			return false;
		}
		if (StringUtils.isEmpty(this.password)) {
			return false;
		}
		return true;
	}

	/**
	 * 检查是否合法登录
	 * 
	 * @param request 请求对象
	 * @return 是否合法登录
	 */
	public boolean checkAuth(HttpServletRequest request) {
		if (!this.isEnableAuth()) {
			throw new BasicAuthNotEnabledException("未启用认证!");
		}
		String authorization = request.getHeader("Authorization");
		if (StringUtils.isEmpty(authorization)) {
			return false;
		}
		return checkAuth(authorization, username, password);
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
	protected boolean checkAuth(String authorization, String username, String password) {
		if (StringUtils.isEmpty(authorization)) {
			throw new BasicAuthException("authorization不能为空!");
		}
		if (StringUtils.isEmpty(username)) {
			throw new BasicAuthException("username不能为空!");
		}
		if (StringUtils.isEmpty(password)) {
			throw new BasicAuthException("password不能为空!");
		}

		String[] basicArray = authorization.split("\\s+");
		if (isBadArray(basicArray)) {
			throw new BasicAuthException("非法authorization[" + authorization + "].!");
		}
		String idpass = Base64.decode(basicArray[1]);
		// String idpass = Encode.base64Decode(basicArray[1]);
		if (StringUtils.isEmpty(idpass)) {
			throw new BasicAuthException("非法authorization[" + idpass + "].!");
		}
		String[] idpassArray = idpass.split(":");
		if (isBadArray(idpassArray)) {
			throw new BasicAuthException("非法authorization[" + idpass + "].!");
		}
		boolean authenticated = username.equalsIgnoreCase(idpassArray[0]) && password.equalsIgnoreCase(idpassArray[1]);
		if (!authenticated) {
			throw new BasicAuthException("账号或密码不正确[username:" + idpassArray[0] + " password:" + idpassArray[1] + "].");
		}
		return true;
	}
}
