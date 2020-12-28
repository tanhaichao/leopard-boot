package io.leopard.boot.timer.locker;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
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

	@PostConstruct
	public void init() {
		this.acquire();
	}

	/**
	 * 获取锁
	 */
	public boolean acquire() {
		// FIXME 这里应该重试N次，旧服务器有可能还没有关闭，新服务器就启动
		String serverIp = ServerIpUtil.getServerIp();
		Long result = redis.setnx(KEY, serverIp);
		boolean success = NumberUtil.toBool(result);
		logger.info("acquire serverIp:" + serverIp + " success:" + success);

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
			Long result = redis.expire(KEY, 15);
			boolean success = NumberUtil.toBool(result);
			logger.info("update serverIp:" + serverIp + " success:" + success);
			if (!success) {
				break;
			}
			try {
				Thread.sleep(5 * 1000);// 5秒钟执行一次
			}
			catch (InterruptedException e) {
				logger.error(e.getMessage(), e);
				break;
			}
		}
	}
}
