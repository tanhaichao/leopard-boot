package io.leopard.boot.rocketmq;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.bean.ConsumerBean;
import com.aliyun.openservices.ons.api.bean.Subscription;

@Component("leopardBootRocketmqConsumer")
@ConditionalOnProperty(prefix = "rocketmq", name = "host")
public class RocketmqConsumer {
	protected Log logger = LogFactory.getLog(this.getClass());
	@Value("${aliyun.rocketmq.namesrv_addr}")
	private String url;
	@Value("${aliyun.rocketmq.accessKeyId}")
	private String accessKey;
	@Value("${aliyun.rocketmq.secretAccessKey}")
	private String secretKey;

	// private String consumerGroup;

	// @Value("${rocketmq.env:}")
	// private String env;

	// private String server;

	private Map<String, ConsumerBean> groupMap = new ConcurrentHashMap<>();
	// /**
	// * 消费者组
	// */
	// public static final String CONSUMER_GROUP = "test_consumer";

	@PostConstruct
	public void init() {

	}

	@PostConstruct
	public void destroy() {
		for (Entry<String, ConsumerBean> entry : groupMap.entrySet()) {
			ConsumerBean consumerBean = entry.getValue();
			consumerBean.shutdown();
		}
	}

	protected ConsumerBean getConsumerBean(String groupId) {
		ConsumerBean consumerBean = groupMap.get(groupId);
		if (consumerBean != null) {
			return consumerBean;
		}
		return this.createConsumerBean(groupId);
	}

	protected synchronized ConsumerBean createConsumerBean(String groupId) {
		{
			ConsumerBean consumerBean = groupMap.get(groupId);
			if (consumerBean != null) {
				return consumerBean;
			}
		}
		Properties properties = new Properties();
		properties.setProperty(PropertyKeyConst.AccessKey, this.accessKey);
		properties.setProperty(PropertyKeyConst.SecretKey, this.secretKey);
		properties.setProperty(PropertyKeyConst.NAMESRV_ADDR, this.url);
		properties.setProperty(PropertyKeyConst.GROUP_ID, groupId);
		// 将消费者线程数固定为20个 20为默认值
		properties.setProperty(PropertyKeyConst.ConsumeThreadNums, "20");

		ConsumerBean consumerBean = new ConsumerBean();
		consumerBean.setProperties(properties);
		Map<Subscription, MessageListener> subscriptionTable = new LinkedHashMap<>();
		consumerBean.setSubscriptionTable(subscriptionTable);
		consumerBean.start();
		return consumerBean;
	}

	// public void subscribe(String topic, String tag, RocketmqMessageListener messageListener) {
	// this.subscribe(consumerGroup, topic, tag, messageListener);
	// }

	public void subscribe(String groupId, String topic, String tag, RocketmqMessageListener messageListener) {
		ConsumerBean consumerBean = this.getConsumerBean(groupId);
		consumerBean.subscribe(topic, tag, new MessageListener() {
			@Override
			public Action consume(Message message, ConsumeContext context) {
				try {
					messageListener.consumeMessage(message);
					return Action.CommitMessage;
				}
				catch (Exception e) {
					logger.error(e.getMessage(), e);
					return Action.ReconsumeLater;
				}
			}
		});

		// if (!consumerBean.isStarted()) {
		// consumerBean.start();
		// }
		logger.info("消费者[groupId:" + groupId + " topic:" + topic + "] 启动成功=======");
	}
}
