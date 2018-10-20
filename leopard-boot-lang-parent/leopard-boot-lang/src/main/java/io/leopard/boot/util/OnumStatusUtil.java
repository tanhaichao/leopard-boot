package io.leopard.boot.util;

import io.leopard.core.exception.forbidden.StatusForbiddenException;
import io.leopard.lang.inum.Onum;

/**
 * 枚举状态检查工具类
 * 
 * @author 谭海潮
 *
 */
public class OnumStatusUtil {

	/**
	 * 判断当前状态是等于某个状态
	 * 
	 * @param currentStatus 当前状态
	 * @param status
	 * @throws StatusForbiddenException
	 */
	public static <E extends Onum<?, String>> void equals(E currentStatus, E status) throws StatusForbiddenException {
		if (currentStatus == null) {
			throw new StatusForbiddenException("当前状态不允许为空.");
		}
		if (currentStatus.getKey().toString().equals(status.getKey().toString())) {
			return;
		}
		throw new StatusForbiddenException("当前状态[" + currentStatus.getKey() + "]不是" + status.getDesc() + "状态.");
	}

	/**
	 * 判断当前状态是否存在statusList中
	 * 
	 * @param currentStatus 当前状态
	 * @param errorStatus 当前状态步骤statusList中，错误提示信息使用改状态的状态描述信息
	 * @param statusList 状态列表
	 * @throws StatusForbiddenException
	 */
	public static <E extends Onum<?, String>> void within(E currentStatus, E errorStatus, @SuppressWarnings("unchecked") E... statusList) throws StatusForbiddenException {
		if (currentStatus == null) {
			throw new StatusForbiddenException("当前状态不允许为空.");
		}
		if (statusList.length == 0) {
			throw new IllegalArgumentException("动态参数个数不能为0.");
		}
		String key = currentStatus.getKey().toString();
		for (E status : statusList) {
			if (status.getKey().toString().equals(key)) {
				return;
			}
		}
		throw new StatusForbiddenException("当前状态[" + currentStatus.getKey() + "]不是" + errorStatus.getDesc() + "状态.");
	}

}
