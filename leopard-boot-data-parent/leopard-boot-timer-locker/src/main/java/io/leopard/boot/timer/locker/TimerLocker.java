package io.leopard.boot.timer.locker;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import io.leopard.boot.util.NumberUtil;
import io.leopard.boot.util.ServerIpUtil;
import io.leopard.redis.Redis;

/**
 * 定时器锁
 * 
 * @author 谭海潮
 *
 */
@Component
public class TimerLocker {

	protected Log logger = LogFactory.getLog(this.getClass());

	private static final String KEY = "TIMER_LOCKER";

	private Redis redis;

	/**
	 * 是否允许运行定时器
	 * 
	 * @return
	 */
	public boolean isAllowRunTimer() {
		String serverIp = ServerIpUtil.getServerIp();
		String ip = redis.get(KEY);
		return serverIp.equals(ip);
	}

	/**
	 * 获取锁
	 */
	public boolean acquire() {
		String serverIp = ServerIpUtil.getServerIp();
		Long result = redis.setnx(KEY, serverIp);
		boolean success = NumberUtil.toBool(result);
		if (!success) {
			return false;
		}

		new Thread() {
			@Override
			public void run() {
				update(serverIp);
			}
		}.start();
		return true;
	}

	protected void update(String serverIp) {
		while (true) {
			Long result = redis.expire(KEY, 60);
			if (!NumberUtil.toBool(result)) {
				break;
			}
			try {
				Thread.sleep(10 * 1000);// 10秒钟执行一次
			}
			catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				break;
			}
		}
	}
}
