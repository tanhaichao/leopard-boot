package io.leopard.boot.util;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

/**
 * 参数合法性验证.
 * 
 * @author 阿海
 * 
 */
public class LeopardValidUtil {

	private static Pattern USERNAME_PATTERN = Pattern.compile("^[a-z0-9][a-z0-9_\\-]+$");

	public static boolean isValidUsername(String username) {
		if (username == null || username.length() == 0) {
			return false;
		}
		// return UdbConstant.isValidUsername(username);
		Matcher m = USERNAME_PATTERN.matcher(username);
		return m.find();
	}

	/**
	 * 是否非法的用户名
	 * 
	 * @param username
	 * @return
	 */
	public static boolean isNotValidUsername(String username) {
		return !isValidUsername(username);
	}

	public static boolean isValidPassport(String passport) {
		return LeopardValidUtil.isValidUsername(passport);
	}

	/**
	 * 验证uid是否合法 .
	 * 
	 * @param uid
	 * @return 合法 false 非法true
	 */
	public static boolean isValidUid(long uid) {
		if (uid <= 0) {
			return false;
		}
		return true;
	}

	private static Pattern IP_PATTERN = Pattern.compile("^[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}$");

	/**
	 * 是否合法IP？ .
	 * 
	 * TODO 方法实现需要优化
	 * 
	 * @param ip
	 * @return
	 */
	public static boolean isValidIp(String ip) {
		if (ip == null || ip.length() == 0) {
			return false;
		}
		if (ip.length() < 7) {
			return false;
		}
		Matcher m = IP_PATTERN.matcher(ip);
		return m.find();
	}

	/**
	 * 是否合法的时间格式？ .
	 * 
	 * @param datetime
	 * @return
	 */
	public static boolean isValidDateTime(String datetime) {
		return DateTime.isDateTime(datetime);
	}

	/**
	 * 判断对象是否为null..
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNull(Object obj) {
		return obj == null;
	}

	/**
	 * 判断对象是否不为null..
	 * 
	 * @param obj
	 * @return
	 */
	public static boolean isNotNull(Object obj) {
		return obj != null;
	}

	private static String ID_REGEX = "^[a-zA-Z0-9_\\-]{32}$";

	/**
	 * 允许非法的uuid
	 */
	private static boolean allowInvalidUuid = false;

	public static void setAllowInvalidUuid(boolean allowInvalidUuid) {
		LeopardValidUtil.allowInvalidUuid = allowInvalidUuid;
	}

	/**
	 * 是否合法的uuid
	 * 
	 * @param uuid
	 * @return
	 */
	public static boolean isUuid(String uuid) {
		if (StringUtils.isEmpty(uuid)) {
			return false;
		}
		if (allowInvalidUuid) {
			return true;
		}
		return uuid.matches(ID_REGEX);
	}

	private static Set<String> MOBILE_PREFIX_SET = new HashSet<String>();
	static {
		MOBILE_PREFIX_SET.add("134");
		MOBILE_PREFIX_SET.add("135");
		MOBILE_PREFIX_SET.add("136");

		MOBILE_PREFIX_SET.add("137");
		MOBILE_PREFIX_SET.add("138");
		MOBILE_PREFIX_SET.add("139");

		MOBILE_PREFIX_SET.add("133");
		MOBILE_PREFIX_SET.add("132");
		MOBILE_PREFIX_SET.add("131");
		MOBILE_PREFIX_SET.add("130");

		MOBILE_PREFIX_SET.add("147");

		MOBILE_PREFIX_SET.add("150");
		MOBILE_PREFIX_SET.add("151");
		MOBILE_PREFIX_SET.add("152");
		MOBILE_PREFIX_SET.add("153");
		MOBILE_PREFIX_SET.add("155");
		MOBILE_PREFIX_SET.add("156");
		MOBILE_PREFIX_SET.add("157");
		MOBILE_PREFIX_SET.add("158");
		MOBILE_PREFIX_SET.add("159");

		MOBILE_PREFIX_SET.add("176");
		MOBILE_PREFIX_SET.add("177");
		MOBILE_PREFIX_SET.add("178");
		MOBILE_PREFIX_SET.add("179");// 这个有吗?

		MOBILE_PREFIX_SET.add("180");
		MOBILE_PREFIX_SET.add("181");
		MOBILE_PREFIX_SET.add("182");
		MOBILE_PREFIX_SET.add("183");
		MOBILE_PREFIX_SET.add("185");
		MOBILE_PREFIX_SET.add("186");
		MOBILE_PREFIX_SET.add("187");
		MOBILE_PREFIX_SET.add("188");
		MOBILE_PREFIX_SET.add("189");

		MOBILE_PREFIX_SET.add("145");
	}

	public static boolean isMobile(String mobile) {
		if (StringUtils.isEmpty(mobile)) {
			return false;
		}
		boolean matches = mobile.matches("^[0-9]{11}$");
		if (!matches) {
			return false;
		}

		String prefix = mobile.substring(0, 3);
		if (MOBILE_PREFIX_SET.contains(prefix)) {
			return true;
		}
		return false;
	}
}
