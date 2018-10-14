package io.leopard.boot.util;

/**
 * UUID
 * 
 * @author 谭海潮
 *
 */
public class UuidUtil {
	private static String ID_REGEX = "^[a-zA-Z0-9_\\-]{32}$";

	/**
	 * 允许非法的uuid
	 */
	private static boolean allowInvalidUuid = false;

	public static void setAllowInvalidUuid(boolean allowInvalidUuid) {
		UuidUtil.allowInvalidUuid = allowInvalidUuid;
	}

	/**
	 * 是否合法的uuid
	 * 
	 * @param uuid
	 * @return
	 */
	public static boolean isUuid(String uuid) {
		if (uuid == null || uuid.isEmpty()) {
			return false;
		}
		if (allowInvalidUuid) {
			return true;
		}
		return uuid.matches(ID_REGEX);
	}
}
