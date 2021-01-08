package io.leopard.boot.rocketmq;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.aliyun.openservices.ons.api.Message;

public abstract class AbstractSubscriber implements RocketmqMessageListener {
	protected Log logger = LogFactory.getLog(this.getClass());

	@Autowired
	protected RocketmqConsumer rocketmqConsumer;

	@PostConstruct
	public void init() {
		rocketmqConsumer.subscribe("GID_DOCTOR_UPDATE_CONSUMER", "doctor_update", "*", this);
	}

	@Override
	public void consumeMessage(Message message) throws Exception {
		// logger.info("consumeMessage:" + message.getKey());
		String json = new String(message.getBody(), "UTF-8");
		this.consumeMessage(message, json);
	}

	public abstract void consumeMessage(Message message, String json) throws Exception;

}
