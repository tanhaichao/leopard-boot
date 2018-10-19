package io.leopard.boot.role;

import org.springframework.web.method.HandlerMethod;

/**
 * 角色util类
 * 
 * @author 谭海潮
 *
 */
public class RoleUtil {

	/**
	 * 是否不需要做角色检查的方法
	 * 
	 * @param handler
	 * @return
	 */
	public static boolean isNoRoleCheckMethod(Object handler) {
		if (!(handler instanceof HandlerMethod)) {
			return false;
		}
		HandlerMethod method = (HandlerMethod) handler;
		return method.hasMethodAnnotation(NoRoleCheck.class);
	}
}
