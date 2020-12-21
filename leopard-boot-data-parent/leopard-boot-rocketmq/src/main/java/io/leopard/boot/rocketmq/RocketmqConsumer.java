package io.leopard.boot.rocketmq;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component("leopardBootRocketmqConsumer")
@ConditionalOnProperty(prefix = "rocketmq", name = "host")
public class RocketmqConsumer {
	protected Log logger = LogFactory.getLog(this.getClass());
	@Value("${rocketmq.host}")
	private String host;

	@Value("${rocketmq.port}")
	private int port;

	@Value("${rocketmq.consumerGroup}")
	private String consumerGroup;

	private String server;
	// /**
	// * 消费者组
	// */
	// public static final String CONSUMER_GROUP = "test_consumer";

	@PostConstruct
	public void init() {
		this.server = this.host + ":" + this.port;
	}

	public void subscribe(String topic, String tag, RocketmqMessageListener messageListener) throws MQClientException {
		this.subscribe(consumerGroup, topic, tag, messageListener);
	}

	public void subscribe(String consumerGroup, String topic, String tag, RocketmqMessageListener messageListener) throws MQClientException {
		DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(consumerGroup);
		consumer.setNamesrvAddr(server);
		// 消费模式:一个新的订阅组第一次启动从队列的最后位置开始消费 后续再启动接着上次消费的进度开始消费
		consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_LAST_OFFSET);
		// 订阅主题和 标签（ * 代表所有标签)下信息
		consumer.subscribe(topic, tag);
		// //注册消费的监听 并在此监听中消费信息，并返回消费的状态信息
		consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
			// msgs中只收集同一个topic，同一个tag，并且key相同的message
			// 会把不同的消息分别放置到不同的队列中

			for (Message msg : msgs) {
				try {
					messageListener.consumeMessage(msg);
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
					return ConsumeConcurrentlyStatus.RECONSUME_LATER;
				}
				// 消费者获取消息 这里只输出 不做后面逻辑处理
				// logger.info("Consumer-获取消息-主题topic为={}, 消费消息为={}", msg.getTopic(), body);
			}

			return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
		});

		consumer.start();
		logger.info("消费者[" + topic + "] 启动成功=======");
	}
}
