package io.leopard.boot.data.queue;

import java.util.Timer;
import java.util.TimerTask;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import io.leopard.redis.RedisImpl;

public class QueueRedisImpl implements Queue {

	private Log logger = LogFactory.getLog(this.getClass());

	private RedisImpl redis;

	protected String server;

	protected String password;
	protected int maxActive = 16;
	protected int timeout = 1000 * 10;

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getMaxActive() {
		return maxActive;
	}

	public void setMaxActive(int maxActive) {
		this.maxActive = maxActive;
	}

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public void init() {
		redis = new RedisImpl(server, maxActive, timeout);
		redis.init();
	}

	public void destroy() {
		redis.destroy();
	}

	@Override
	public void publish(String routingKey, String message) {
		redis.rpush(routingKey, message);
	}

	@Override
	public void subscribe(final String queue, final IConsumer callback) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				try {
					subscribe2(queue, callback);
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
				}
			}
		}, 0, 1000);

	}

	private void subscribe2(final String queue, final IConsumer callback) {
		while (true) {
			String message = redis.lpop(queue);
			if (message == null) {
				break;
			}
			try {
				callback.consume(message);
			}
			catch (Exception e) {
				logger.error("message:" + message);
				logger.error(e.getMessage(), e);
			}
		}
	}

}
