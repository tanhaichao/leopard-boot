package io.leopard.boot.rocketmq;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;

import com.aliyun.openservices.ons.api.Action;
import com.aliyun.openservices.ons.api.ConsumeContext;
import com.aliyun.openservices.ons.api.Consumer;
import com.aliyun.openservices.ons.api.Message;
import com.aliyun.openservices.ons.api.MessageListener;
import com.aliyun.openservices.ons.api.ONSFactory;
import com.aliyun.openservices.ons.api.PropertyKeyConst;
import com.aliyun.openservices.ons.api.PropertyValueConst;

import io.leopard.json.Json;

public abstract class Subscriber implements MessageListener {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Value("${aliyun.rocketmq.namesrv_addr}")
	private String url;
	@Value("${aliyun.rocketmq.accessKeyId}")
	private String accessKey;
	@Value("${aliyun.rocketmq.secretAccessKey}")
	private String secretKey;

	protected abstract Subscription getSubscription();

	private Consumer consumer;

	@PostConstruct
	public void init() {
		logger.info("subscribe start...");
		Subscription subscription = this.getSubscription();
		if (subscription == null) {
			throw new RuntimeException("订阅信息不能为空.");
		}
		String groupId = subscription.getGroupId();
		String topic = subscription.getTopic();
		String expression = subscription.getExpression();
		Properties properties = new Properties();
		// 您在控制台创建的Group ID。
		if (groupId != null) {
			properties.put(PropertyKeyConst.GROUP_ID, groupId);
		}
		// AccessKey ID阿里云身份验证，在阿里云RAM控制台创建。
		properties.put(PropertyKeyConst.AccessKey, accessKey);
		// Accesskey Secret阿里云身份验证，在阿里云服RAM控制台创建。
		properties.put(PropertyKeyConst.SecretKey, secretKey);
		// 设置TCP接入域名，进入控制台的实例详情页面的TCP协议客户端接入点区域查看。
		properties.put(PropertyKeyConst.NAMESRV_ADDR, url);
		// 集群订阅方式(默认)。
		properties.put(PropertyKeyConst.MessageModel, PropertyValueConst.CLUSTERING);

		this.consumer = ONSFactory.createConsumer(properties);
		consumer.subscribe(topic, expression, this);
		consumer.start();
		logger.info("subscribe " + topic + " subscription:" + Json.toJson(subscription));
	}

	@PreDestroy
	public void destroy() {
		if (consumer != null) {
			consumer.shutdown();
		}
	}

	@Override
	public Action consume(Message message, ConsumeContext context) {
		try {
			String json = new String(message.getBody(), "UTF-8");
			logger.info("consume json:" + json);
			this.consumeMessage(message, context, json);
			return Action.CommitMessage;
		}
		catch (Exception e) {
			logger.error(e.getMessage(), e);
			return Action.ReconsumeLater;
		}
	}

	public abstract void consumeMessage(Message message, ConsumeContext context, String json) throws Exception;
}
