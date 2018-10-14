package io.leopard.boot.onum.dynamic.service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.leopard.boot.onum.dynamic.model.DynamicEnumQueueMessage;
import io.leopard.json.Json;
import io.leopard.redis.Redis;
import redis.clients.jedis.JedisPubSub;

/**
 * 动态枚举值更新订阅器
 * 
 * @author 谭海潮
 *
 */
@Component
public class DynamicEnumSubscriber extends JedisPubSub {
	protected Log logger = LogFactory.getLog(this.getClass());

	private static final String CHANNEL = "pubsub:dynamic_enum";

	@Resource(name = "redis") // FIXME sessionRedis
	Redis redis;

	@Autowired
	private DynamicEnumService dynamicEnumService;

	/**
	 * 消息发送者
	 */
	private String sender;

	@PostConstruct
	public void init() throws InterruptedException {
		this.sender = Integer.toString(redis.hashCode());

		new Thread() {
			@Override
			public void run() {
				// Exception in thread "Thread-11" java.lang.ClassCastException: [B cannot be cast to java.util.List
				try {
					subscribe();
				}
				catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			};
		}.start();

	}

	/**
	 * 订阅Redis消息队列
	 * 
	 * @throws InterruptedException
	 */
	protected void subscribe() throws InterruptedException {
		while (true) {
			try {
				redis.subscribe(this, CHANNEL);
			}
			catch (ClassCastException e) {
				// FIXME 为什么会报错?
				// logger.error(e.getMessage(), e);
				Thread.sleep(1000);
			}
		}
	} // 取得订阅的消息后的处理

	/**
	 * 发布更新通知
	 * 
	 * @return
	 */
	public boolean publish() {
		boolean success = dynamicEnumService.rsync();
		publish("update");
		return success;
	}

	/**
	 * 发布消息
	 * 
	 * @param msg
	 * @return
	 */
	protected boolean publish(String msg) {
		DynamicEnumQueueMessage message = new DynamicEnumQueueMessage();
		message.setMessage(msg);
		message.setSender(sender);
		String json = Json.toJson(message);
		redis.publish(CHANNEL, json);
		return true;
	}

	@Override
	public void onMessage(String channel, String json) {
		DynamicEnumQueueMessage message = Json.toObject(json, DynamicEnumQueueMessage.class);
		boolean isMySelf = this.sender.equals(message.getSender());
		if (isMySelf) {
			return;
		}
		logger.info("subscribe message:" + message + " isMySelf:" + isMySelf);
		dynamicEnumService.rsync();
	}
}
